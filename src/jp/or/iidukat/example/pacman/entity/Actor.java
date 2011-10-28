package jp.or.iidukat.example.pacman.entity;

import jp.or.iidukat.example.pacman.Direction;
import jp.or.iidukat.example.pacman.PacmanGame;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Actor extends BaseEntity {

    static final int DEFAULT_DISPLAY_ORDER = 110;

    final PacmanGame game;
    float[] pos;
    private float[] elPos;
    private int[] elBackgroundPos;
    Direction dir = Direction.NONE;

    Actor(Bitmap sourceImage, PacmanGame game) {
        super(sourceImage);
        this.game = game;
    }

    void init() {
        Appearance a = getAppearance();
        a.setWidth(16);
        a.setHeight(16);
        a.setTopOffset(-4);
        a.setLeftOffset(-4);
        a.prepareBkPos(0, 0);
        a.setOrder(DEFAULT_DISPLAY_ORDER);

        this.elPos = new float[] { 0, 0 };
        this.elBackgroundPos = new int[] { 0, 0 };
    }

    public abstract void move();

    // Actor表示画像切り替え(アニメーション対応)&表示位置更新
    public final void updateAppearance() {
        updateElPos(); //位置移動 
        int[] bgPos = { 0, 0 };
        bgPos = canAppear() ? getImagePos() : new int[] { 0, 3 };
        if (this.elBackgroundPos[0] != bgPos[0] || this.elBackgroundPos[1] != bgPos[1]) {
            this.elBackgroundPos[0] = bgPos[0];
            this.elBackgroundPos[1] = bgPos[1];
            bgPos[0] *= 16;
            bgPos[1] *= 16;
            getAppearance().changeBkPos(bgPos[1], bgPos[0], true);
        }
    }

    abstract boolean canAppear();
    abstract int[] getImagePos();
    
    // 表示位置更新
    public final void updateElPos() {
        float b = getFieldX();
        float c = getFieldY();
        if (this.elPos[0] != c || this.elPos[1] != b) {
            this.elPos[0] = c;
            this.elPos[1] = b;
            Appearance el = getAppearance();
            el.setLeft(b);
            el.setTop(c);
        }
    }

    public abstract float getFieldX();
    public abstract float getFieldY();
    
    @Override
    final void doDraw(Canvas canvas) {
        getAppearance().drawBitmap(canvas);
    }

}
