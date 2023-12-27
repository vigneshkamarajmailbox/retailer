package com.botree.retailerssfa.speechview.animators;

import com.botree.retailerssfa.speechview.RecognitionBar;

import java.util.ArrayList;
import java.util.List;

public class RmsAnimator implements BarParamsAnimator {
    private final List<BarRmsAnimator> barAnimators;


    public RmsAnimator(List<RecognitionBar> recognitionBars) {
        this.barAnimators = new ArrayList<>();
        for (RecognitionBar bar : recognitionBars) {
            barAnimators.add(new BarRmsAnimator(bar));
        }
    }

    @Override
    public void start() {
        for (BarRmsAnimator barAnimator : barAnimators) {
            barAnimator.start();
        }
    }

    @Override
    public void stop() {
        for (BarRmsAnimator barAnimator : barAnimators) {
            barAnimator.stop();
        }
    }

    @Override
    public void animate() {
        for (BarRmsAnimator barAnimator : barAnimators) {
            barAnimator.animate();
        }
    }

    public void onRmsChanged(float rmsDB) {
        for (BarRmsAnimator barAnimator : barAnimators) {
            barAnimator.onRmsChanged(rmsDB);
        }
    }
}