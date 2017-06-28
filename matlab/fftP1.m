function [ Y ] = fftP1( X )
L = length(X);

Xfft = fft(X);

P2 = abs(Xfft/L);
Y = P2(1:L/2+1);
Y(2:end-1) = 2*Y(2:end-1);
end

