package main.skyline;

import main.Line;

class SkyLineContainer {

    public SkyLine skyLine = null;
    public int index = 0;
    public int size = 0;
    public Line currentLine;

    public SkyLineContainer(SkyLine _skyLine) {
        skyLine = _skyLine;
        size = skyLine.size();
        currentLine = skyLine.getLine(index);
    }

    /**
     * currentLine을 skyLine의 다음 element로 설정
     */
    public void next() {
        index++;

        if (!isFinished()) {
            currentLine = skyLine.getLine(index);
        }
    }

    /**
     * skyLine의 모든 line들을 iterate 했는 지 여부 반환
     * @return isFinished
     */
    public boolean isFinished() {
        return index >= size;
    }

}