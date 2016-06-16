package com.zc741.welcome;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.meetic.dragueur.Direction;
import com.meetic.dragueur.DraggableView;
import com.meetic.dragueur.ViewAnimator;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        DraggableView draggableView = (DraggableView) findViewById(R.id.draguer);
        draggableView.setRotationEnabled(true);
        draggableView.setRotationValue(10f);
        draggableView.setDragListener(new DraggableView.DraggableViewListener() {
            @Override
            public void onDrag(DraggableView draggableView, float percentX, float percentY) {

            }

            @Override
            public void onDraggedStarted(DraggableView draggableView, Direction direction) {

            }

            @Override
            public void onDraggedEnded(DraggableView draggableView, Direction direction) {

            }

            @Override
            public void onDragCancelled(DraggableView draggableView) {

            }
        });

        draggableView.setViewAnimator(new ViewAnimator() {
            @Override
            public boolean animateExit(@NonNull DraggableView draggableView, Direction direction, int duration) {
                return false;
            }

            @Override
            public boolean animateToOrigin(@NonNull DraggableView draggableView, int duration) {
                return false;
            }

            @Override
            public void update(@NonNull DraggableView draggableView, float percentX, float percentY) {

            }
        });
    }
}
