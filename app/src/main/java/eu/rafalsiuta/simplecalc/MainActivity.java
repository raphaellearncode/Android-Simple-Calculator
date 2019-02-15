package eu.rafalsiuta.simplecalc;
/**
 * @author Rafal Siuta
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mainBtn;

    private TextView display, miniDisplay;


    /**
     * array of arrays- different button groups id-s depend from action methods added in onClick Listener
     */
    private final int[][] btnId = {{R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,R.id.six,R.id.seven,R.id.eight,R.id.nine,R.id.zero}, //case 0: numeric buttons
            {R.id.plus,R.id.minus,R.id.div,R.id.multiple},                                                                                //case 1: operator buttons
            {R.id.plusminus,R.id.eql,R.id.perc,R.id.sqrt,R.id.pow},                                                                       //case 2: special operator
            {R.id.sets,R.id.cls,R.id.del,R.id.dot}};

    private List<Button> btnList = new ArrayList<>();

    public static DecimalFormat nf = new DecimalFormat("#.##########################################");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Font.setFont(this);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        /**
         * this method trigger <code>android:ellipsize="marquee"</code> long text display animation
         */
        display.setSelected(true);

        miniDisplay = findViewById(R.id.miniDisplay);

        finderId(mainBtn,btnId,btnList);

    }

    /**
     *
     * @param btn
     * @param id
     * @param list
     * for each loop geting arrays from array than get each id from all arrays, seting listener and add to button list
     */
    private void finderId(Button btn, int[][] id, List<Button> list){

        for(int[] idx: id){
            for(int idy:idx){
                btn = findViewById(idy);
                btn.setOnClickListener(this);
                list.add(btn);

            }
        }
    }

    @Override
    public void onClick(View v) {
        /**
         * switch array button groups for different methods
         */
        for(int i = 0;i<btnId.length;i++){
            switch (i){
                case 0: for(int j = 0;j<btnId[i].length;j++){
                    if(v.getId() == btnId[i][j]){
                        Logic.onNumbers(v,display);

                    }
                }break;
                case 1: for(int j = 0;j<btnId[i].length;j++){
                    if(v.getId() == btnId[i][j]){
                        Logic.onOperator(v,display,nf);

                    }
                }break;
                case 2:for(int j = 0;j<btnId[i].length;j++){ if(v.getId() == btnId[i][j]){
                    if(j == 0){
                        Logic.onPlusminus(display,nf);
                    }else if(j==1){
                        Logic.onEqual(display,nf);
                    }
                    else if(j==2){
                        Logic.onPercent(v,display,nf);
                    }else if(j==3){
                        Logic.onSqrt(display,nf);
                    }else if(j==4){
                        Logic.onPow(display,nf);
                    }
                }
                } break;
                case 3: for(int j = 0;j<btnId[i].length;j++){
                    if(v.getId() == btnId[i][j]){
                        if(j == 0){
                            // onSets();
                        }else if(j==1){
                            Logic.onClear(display);
                        }else if(j==2){
                            Logic.onDelete(display);
                        }else if(j==3){
                            Logic.onDecimal(v,display);
                        }
                    }
                } break;
            }

        }

    }
}
