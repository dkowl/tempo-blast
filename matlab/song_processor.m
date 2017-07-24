clear;

songName = 'ed-sheeran_shape-of-you';
songFormat = '.wav';
songFolder = 'C:\Users\Dominik\Desktop\SuperTempo\matlab\music\';
songPath = strcat(songFolder, songName, songFormat);

[song, fs] = audioread(songPath);
%song = song(1:1000000);
L = length(song);
windowL = 512;
hannWindow = hann(windowL);
hannOverlap = 0.5;
step = (1-hannOverlap) * windowL;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%calculating the filterbank
filterNo = 26;
filterL = windowL/2 + 1;
filterbank = zeros([filterL, filterNo]);
loFreq = 80;
hiFreq = fs;

melPoints = zeros([filterNo + 2, 1]);
freqPoints = zeros([filterNo + 2, 1]);
binPoints = zeros([filterNo + 2, 1]);
loMel = mel(loFreq);
hiMel = mel(hiFreq);
for j = 1:filterNo + 2
    melPoints(j) = loMel + (hiMel-loMel)*((j-1)/(filterNo+1));
    freqPoints(j) = invMel(melPoints(j));
    binPoints(j) = floor(freqPoints(j)/(fs/(filterL-1)));
end

for i = 1:filterNo
    beginBin = binPoints(i);
    peakBin = binPoints(i+1);
    endBin = binPoints(i+2);
    
    for j = beginBin+1:endBin-1
        if j <= peakBin
            value = (j - beginBin) / (peakBin - beginBin);
        elseif j > peakBin
            value = (endBin - j) / (endBin - peakBin);
        end
        filterbank(uint32(j), uint32(i)) = value;
    end
end
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
totalWindows = floor(L/step)-1;
filterEnergy = zeros([filterNo, totalWindows]);

prevWinFft = zeros(1, windowL);

%calculating filter energies
tic
for winNo = (1:floor(L/step)-1)
    
    winStart = 1 + (winNo-1)*step;
    winEnd = winStart + windowL-1;
    windowData = song(winStart:winEnd);
    windowData = windowData .* hannWindow;
    winFft = fftP1(windowData);    
    
    for j = 1:filterNo
        energy = 0;
        for k = 1:filterL
                energy = energy + winFft(k)*filterbank(k, j);
        end
        filterEnergy(j, winNo) = log(energy*1000);
    end
end
toc

%smoothing the data
multiplier = 0.5;

currentEMA = filterEnergy(:, 1);
filterEMA = zeros([filterNo, totalWindows]);
filterEMA(:, 1) = currentEMA;

for i = (2:totalWindows)
    filterEMA(:, i) = currentEMA;
    for j = (1:filterNo)
        currentEMA(j) = currentEMA(j) + (filterEnergy(j, i) - currentEMA(j)) * multiplier;
    end
end
toc

%%%%%%%%%%%%%%%
%note detection
noteHit = zeros([filterNo, totalWindows-1]);
retention = 0.01; %how long a note lasts
threshold = zeros(1, filterNo);
for i = (1:filterNo)
    threshold(i) = 0.0;%(1.5 - 0.5*i/filterNo)*multiplier;
end
prevFilterEnergy = filterEMA(:, 1);
frameTime = step / fs;

for i = (2:totalWindows)    
    diffFilterEnergy = filterEMA(:, i) - prevFilterEnergy;
    noteHit(:, i-1) = diffFilterEnergy;
    
    prevFilterEnergy = filterEMA(:, i);
end

%peak finding
peakWindowSize = 1;
peaks = zeros([filterNo, totalWindows]);
for i = (1+peakWindowSize:totalWindows-1-peakWindowSize)
    for j = (1:filterNo)
        if noteHit(j, i) > noteHit(j, i-1) && noteHit(j, i) > noteHit(j, i+1)
            peaks(j, i) = noteHit(j, i);
        end
    end
end

%peak filtering
peakDropTime = 0.5;
peakThreshold = zeros(size(peaks));
peakDropPerFrame = (windowL/fs)/peakDropTime;
currentThreshold = zeros(1, filterNo);
recentPeak = 1;
for i = (1+peakWindowSize:totalWindows-1-peakWindowSize)
    for j = (1:filterNo)
        if peaks(j, i) > currentThreshold(j)
            currentThreshold(j) = peaks(j, i);
            recentPeak = max(1, peaks(j, i));
        else
            currentThreshold(j) = currentThreshold(j) - peakDropPerFrame * recentPeak;
        end
        if currentThreshold(j) < 0.1
            currentThreshold(j) = 0.1;
        end
        peakThreshold(j, i) = currentThreshold(j);
    end
end
for i = (totalWindows-1-peakWindowSize:-1:1+peakWindowSize)
    for j = (1:filterNo)
        if peaks(j, i) > currentThreshold(j)
            currentThreshold(j) = peaks(j, i);
            recentPeak = max(1, peaks(j, i));
        else
            currentThreshold(j) = currentThreshold(j) - peakDropPerFrame * recentPeak;
        end
        if currentThreshold(j) < 0
            currentThreshold(j) = 0;
        end
        peakThreshold(j, i) = max(peakThreshold(j, i), currentThreshold(j));
    end
end

for i = 1:size(peaks, 2)
    for j = 1:filterNo
        if peaks(j, i)<peakThreshold(j, i)
            peaks(j, i) = 0;
        end
    end
end

%higher frequency emphasis
freqMultiplier = zeros(1, filterNo);
maxMult = 4;
minMult = 1;
for i = (1:filterNo)
    freqMultiplier(i) = maxMult - (2/(i+1))*(maxMult-minMult);
end
for i = 1:totalWindows
    peaks(:, i) = peaks(:, i) .* transpose(freqMultiplier);
end

%note chance
noteChance = zeros(1, totalWindows);
gaussWinSize = 16;
gaussWin = transpose(gausswin(gaussWinSize));
for i = (1:totalWindows-gaussWinSize)
    noteChance(i+gaussWinSize/2) = sqrt(sum(sum(peaks(:, i:i+gaussWinSize-1), 1) .* gaussWin));
end

noteChanceMedian = zeros(1, totalWindows);
noteChanceAverage = zeros(1, totalWindows);
noteChanceThreshold = zeros(1, totalWindows);
medianWindowSize = 100;
for i = 1:totalWindows
    noteChanceMedian(i) = median(noteChance(max(1, i-medianWindowSize):i));
    noteChanceAverage(i) = mean(noteChance(max(1, i-medianWindowSize):i));
    noteChanceThreshold(i) = 2 + noteChanceMedian(i);
end

%note timing
noteTime = zeros(1, totalWindows);
for i = 1:totalWindows
    noteTime(i) = i*windowL/fs;
end

%choosing notes
noteIds = [];
noteChosenChances = [];
for i = 2:totalWindows-1
    if noteChance(i) > noteChance(i-1) && noteChance(i) >= noteChance(i+1) && noteChance(i) > noteChanceThreshold(i)
        noteIds = [noteIds, i];
        noteChosenChances = [noteChosenChances, noteChance(i)];
    end
end

%mapping to key numbers
keyNo = 9;
noteChosenChances = sort(noteChosenChances);
noteKeyThreshold = zeros(1, keyNo);
noteKeyNumber = zeros(1, length(noteIds));
for i = 1:9
    noteKeyThreshold(i) = noteChosenChances(ceil(i*length(noteIds)/keyNo));
end
for i = 1:length(noteIds)
    for keyId = 1:keyNo
        if(noteKeyThreshold(keyId) > noteChance(noteIds(i)))
            break;
        end
    end
    noteKeyNumber(i) = keyId;
end

%writing to file
folderName = 'music/notes/';
noteFileExtension = '.notes';
fileID = fopen(strcat(folderName, songName, noteFileExtension), 'w');
for i = 1:length(noteIds)
    fprintf(fileID, '%.3f, %.3f\n', noteTime(noteIds(i)), noteKeyNumber(i));
end
toc
fclose(fileID);
    


%display loop
slowdown = 1;
windowDisplayStep = 8;
displayOn = false;
playOn = false;
for i = (1:windowDisplayStep:totalWindows)
    if(displayOn == false)
        if(playOn == true)
            sound(song, fs/slowdown);
        end
        break;
    end
    
    subplot(2, 1, 1);
    plot(noteChance(i:min(totalWindows, i+500)));
    hold on
    plot(noteChanceThreshold(i:min(totalWindows, i+500)));
    hold off
    
    subplot(2, 1, 2);
    plot(filterEMA(:, i));
    xlim([0, 30]);
    ylim([0, 7]);
    drawnow;
    
    if i == 1
        sound(song, fs/slowdown);
        tic
    end
    
    waitTime = frameTime*slowdown*i - toc;
    if(waitTime > 0)
        pause(waitTime);
    end
    toc
end


%{
filterIndex = 10;
plot(noteHit(filterIndex, :));
hold on
scatter((1:totalWindows), peaks(filterIndex, :));
hold on
plot(peakThreshold(filterIndex, :));
hold off


figure
subplot(2, 1, 1);
heatmap(peaks);

subplot(2, 1, 2);
plot(noteChance);
%}

