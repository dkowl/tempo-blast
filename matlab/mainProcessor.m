clear

difficultyNames = {
    'easy'
    'medium'
    'hard'
    'insane'
    };

difficultyLevels = [
    0.25
    0.35
    0.5
    1
    ];

musicNames = dir('music/*.wav');

for i = 1:length(musicNames)
    for j = 1:length(difficultyNames)
        processSong(musicNames(i).name(1:end-4), difficultyNames{j}, difficultyLevels(j));
    end
end
