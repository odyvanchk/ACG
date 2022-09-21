package com.example.acg_labs.drawer;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class BrezenhamDrawer {
    private static final BrezenhamDrawer INSTANCE = new BrezenhamDrawer();

    private BrezenhamDrawer(){}

    public static BrezenhamDrawer getInstance() {
        return INSTANCE;
    }

    public void draw(int x0, int y0, int x1, int y1, PixelWriter px) {
            int A, B, sign;
            A = y1 - y0;
            B = x0 - x1;
            if (Math.abs(A) > Math.abs(B))
                sign = 1;
            else
                sign = -1;

            int signa, signb;
            if (A < 0)
                signa = -1;
            else
                signa = 1;

            if (B < 0)
                signb = -1;
            else
                signb = 1;

            int f = 0;
            px.setColor(x0, y0, Color.BLUE);

            int x = x0, y = y0;
            if (sign == -1)
            {
                do {
                    f += A * signa;
                    if (f > 0) {
                        f -= B * signb;
                        y += signa;
                    }
                    x -= signb;
                    px.setColor(x, y, Color.BLUE);
                } while ((x != x1 || y != y1) && !(x0 == x1 && y0 == y1));
            }
            else
            {
                do {
                    f += B * signb;
                    if (f > 0) {
                        f -= A * signa;
                        x -= signb;
                    }
                    y += signa;
                    px.setColor(x, y, Color.BLUE);
                } while ((x != x1 || y != y1) && !(x0 == x1 && y0 == y1));
            }
    }
}
