package com.example.leonardo.testapplication.screenGuide;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.leonardo.testapplication.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.ArrayList;
import java.util.List;

import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;
import me.toptas.fancyshowcase.OnCompleteListener;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

/**
 * Created by leonardo on 29/01/18.
 */

public class ScreenGuide {

    private FragmentActivity fragmentActivity;
    private View[] views;

    public ScreenGuide(FragmentActivity fragmentActivity, View[] views) {
        this.fragmentActivity = fragmentActivity;
        this.views = views;
    }

    /**
     * FancyShowCase
     *
     * Pros:
     * - Cambiar el color.
     * - Cambiar de tamaño la letra.
     * - Cambiar el la figura del target.
     * - Agregar iconos junto al texto.
     * - Agregar layouts personalizados.
     * - Permite encolado de "guías".
     * - Sencillo de usar.
     * - Cambiar las animaciones
     *
     * Cons:
     * - El texto de aparición hay que ubicarlo uno mismo (hasta donde pude).
     */
    public void fancyShowCase() {
        final FancyShowCaseView[] FANCY_SHOW_CASE_VIEW = new FancyShowCaseView[views.length];
        FancyShowCaseQueue mQueue = new FancyShowCaseQueue();

        for (int i = 0; i < views.length; i++) {
            Button btn = (Button) views[i];

            FANCY_SHOW_CASE_VIEW[i] = new FancyShowCaseView.Builder(fragmentActivity)
                    .title(btn.getText().toString())
                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                    .titleGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL)
                    .focusOn(views[i])
                    .build();

            mQueue.add(FANCY_SHOW_CASE_VIEW[i]);
        }

        mQueue.show();

        mQueue.setCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete() {
                Toast.makeText(fragmentActivity.getApplicationContext(),
                        "Todos los FancyShowCaseView mostrados.", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    /**
     *  TabTargetView
     *
     * Pros:
     * - Cambiar el color.
     * - Cambiar de tamaño la letra.
     * - Cambiar las animaciones.
     * - Agregar descripción.
     * - Cambiar el radio del circulo target.
     * - Permite transparencias.
     * - Permite colorear el objeto target
     * - El texto se ubica solo.
     * - Permite encolado de "guías".
     * - Sencillo de usar.
     *
     * Cons:
     * - No se puede cambiar el circulo de target (Hasta donde vi).
     */
    public void tabTargetView() {
        final TapTargetSequence sequence = new TapTargetSequence(fragmentActivity)
                .targets(getTargets())
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        Toast.makeText(fragmentActivity.getApplicationContext(),
                                "Todos los TapTargetView mostrados.", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                });

        sequence.start();
    }

    private List<TapTarget> getTargets() {
        List<TapTarget> tapTargets = new ArrayList<>();

        for (int i = 0; i < views.length; i++) {
            Button btn = (Button) views[i];
            tapTargets.add(
                    TapTarget.forView(views[i], btn.getText().toString(), "Description.")
                            .outerCircleColor(R.color.target)
                            .outerCircleAlpha(0.7f)
                            .targetCircleColor(R.color.outTarget)
                            .titleTextSize(30)
                            .targetRadius(80)
                            .titleTextColor(R.color.mColor1)
                            .descriptionTextSize(20)
                            .descriptionTextColor(R.color.white)
                            //.icon(fragmentActivity.getDrawable(R.drawable.ic_hotel_black_24dp))
                            .drawShadow(true)
                            .tintTarget(false)
                            .transparentTarget(false));
        }

        return tapTargets;
    }

    /**
     * MaterialTapTargetPrompt
     *
     * Pros:
     * - Permite adaptar la figura.
     * - Cambiar tamaño de los textos.
     * - Cambiar los colores.
     *
     * Cons:
     * - No permite encolado.
     * - No admite transparencia.
     * - No supe como crear un "encolado" de guías.
     */
    public void materialTapTargetPrompt() {
        Button btn = (Button) views[4];
        MaterialTapTargetPrompt.Builder materialTapTargetPrompt = new MaterialTapTargetPrompt.Builder(fragmentActivity)
                .setTarget(views[4])
                .setPrimaryText(btn.getText().toString())
                .setPrimaryTextColour(fragmentActivity.getResources().getColor(R.color.mColor1))
                .setSecondaryText("Description")
                .setSecondaryTextColour(fragmentActivity.getResources().getColor(R.color.white))
                .setBackgroundColour(fragmentActivity.getResources().getColor(R.color.colorPrimary))
                .setPromptBackground(new RectanglePromptBackground())
                .setPromptFocal(new RectanglePromptFocal())
                .setAnimationInterpolator(new FastOutSlowInInterpolator());

        materialTapTargetPrompt.show();
    }
}
