function [ X ] = removeElements( X, elementIds )

    tempX = [];
    elemIter = 1;
    for i = 1:length(X)
        nextElemId = elementIds(elemIter);
        if i == nextElemId
            if elemIter < length(elementIds)
                elemIter = elemIter + 1;
            end
        else
            tempX = [tempX, X(i)];
        end
    end
    
    X = tempX;
    
end