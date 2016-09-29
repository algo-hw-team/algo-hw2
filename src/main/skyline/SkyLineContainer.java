package main.skyline;

import main.Line;

class SkyLineContainer {

    public Skyline skyline = null;
    public int index = 0;
    public int size = 0;
    public Line currentLine;

    public SkyLineContainer(Skyline _skyline) {
        skyline = _skyline;
        size = skyline.size();
        currentLine = skyline.getLine(index);
    }

    /**
     * currentLine을 skyLine의 다음 element로 설정
     */
    public void pop() {
        index++;

        currentLine = isFinished() ?
                null :
                skyline.getLine(index);
    }

    /**
     * skyLine의 모든 line들을 iterate 했는 지 여부 반환
     * @return isFinished
     */
    public boolean isFinished() {
        return index >= size;
    }

}