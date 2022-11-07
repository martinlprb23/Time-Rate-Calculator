package com.example.tarifaportiempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class calculadora extends AppCompatActivity {

    private RadioButton rb_hora, rb_min, rb_RHI, rb_RHF;
    private EditText et_tarifa;
    private TextView tv_Hinicial, tv_Hfinal, tv_Htotales, tv_Result, tv_tarifauser;
    private TimePicker timePicker;

    int h_inicio = 100, h_final = 100, m_inicio = 0, m_final = 0, horas = 0, minutos = 0;
    String am = "am", pm = "pm";
    double horas_min_float = 0, tarifa_float = 0, minutostotales_float = 0, result = 0, tarifa = 0;
    DecimalFormat df = new DecimalFormat("#.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rb_hora = (RadioButton) findViewById(R.id.rbtn_hora);
        rb_hora.setChecked(true);
        rb_min = (RadioButton) findViewById(R.id.rbtn_min);
        rb_RHI = (RadioButton) findViewById(R.id.rb_RegistrarInicio);
        rb_RHI.setChecked(true);
        rb_RHF = (RadioButton) findViewById(R.id.rb_RegistradoF);


        tv_Hinicial = (TextView) findViewById(R.id.tv_horaInicial);
        tv_Hfinal = (TextView) findViewById(R.id.tv_horaFinal);
        tv_Htotales = (TextView) findViewById(R.id.tv_HT);
        tv_Result = (TextView) findViewById(R.id.tv_result);
        tv_tarifauser = (TextView) findViewById(R.id.tarifa_user);

        ImageButton imb_teclado = (ImageButton) findViewById(R.id.imb_teclado);

        timePicker = (TimePicker) findViewById(R.id.pikerxd);

        et_tarifa = (EditText) findViewById(R.id.et_tarifauser);


        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);//linea para recuperar lo que se guarda
        et_tarifa.setText(preferences.getString("tarifa", ""));
        tv_Hinicial.setText(preferences.getString("Hini", ""));
        tv_Hfinal.setText(preferences.getString("Hfini", ""));
        tv_Htotales.setText(preferences.getString("TH", ""));

        result = preferences.getFloat("result", (float) result);

        horas = preferences.getInt("horas", horas);
        minutos = preferences.getInt("min", minutos);
        h_inicio = preferences.getInt("h_inicio", h_inicio);
        h_final = preferences.getInt("h_final", h_final);
        m_inicio = preferences.getInt("h_inicio", m_inicio);
        m_final = preferences.getInt("h_inicio", m_final);

        tv_Result.setText("$ " + df.format(result));

        precioT();

        //----------------------------evento del boton ocultar teclado------------------
        imb_teclado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  ocultarteclado(  calculadora.super.getActivity());// esta linea vale oro me hice 2 horas jajaja
                ocultarteclado();

            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {//instancia del piker
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {//metodo para obtener el tiempo
                // Toast.makeText(calculadora.getC,"Time:"+hourOfDay+":"+minute,Toast.LENGTH_SHORT).show();//hora=hourOfDay;// min=minute

                //----------------------------------------------------------radio button hora inicio-------------------------------------------
                if (rb_RHI.isChecked()) {//inicio radio button  horainicio

                    h_inicio = hourOfDay;//guarda la hora inicio
                    m_inicio = minute;//fuarda los minutos inicio

                    if (timePicker.getHour() < 12 && timePicker.getHour() >= 1) {
                        if (timePicker.getMinute() < 10) {//valida los minutos en formato 00
                            tv_Hinicial.setText(hourOfDay + ":0" + minute + " " + am);//imprime en pantalla la hora
                        } else {
                            tv_Hinicial.setText(hourOfDay + ":" + minute + " " + am);//imprime en pantalla la hora
                        }
                    } else if (timePicker.getHour() < 1) {
                        if (timePicker.getMinute() < 10) {//valida los minutos en formato 00
                            tv_Hinicial.setText("12:0" + minute + " " + am);//imprime en pantalla la hora
                        } else {
                            tv_Hinicial.setText("12:" + minute + " " + am);//imprime en pantalla la hora
                        }

                    } else if (timePicker.getHour() == 12) {
                        if (timePicker.getMinute() < 10) {//valida los minutos en formato 00
                            tv_Hinicial.setText(hourOfDay + ":0" + minute + " " + pm);//imprime en pantalla la hora
                        } else {
                            tv_Hinicial.setText(hourOfDay + ":" + minute + " " + pm);//imprime en pantalla la hora
                        }
                    } else {
                        hourOfDay = hourOfDay - 12;//formato de 12 horas ejemplo 15:00-12:00=03:00pm
                        if (timePicker.getMinute() < 10) {//valida los minutos en formato 00
                            tv_Hinicial.setText(hourOfDay + ":0" + minute + " " + pm);//imprime en pantalla la hora
                        } else {
                            tv_Hinicial.setText(hourOfDay + ":" + minute + " " + pm);//imprime en pantalla la hora
                        }
                    }

                }
//------------------------------------------------------radio button hora final-------------------------------------------
                if (rb_RHF.isChecked()) {//final radio button

                    h_final = hourOfDay;//guarda la hora
                    m_final = minute;//guarda los minutos

                    //---------------------------------------------------------------------------------------

                    if (timePicker.getHour() < 12 && timePicker.getHour() >= 1) {//validacion
                        if (timePicker.getMinute() < 10) {//validacion minutos en formato 00.
                            tv_Hfinal.setText(hourOfDay + ":0" + minute + " " + am);//imprime en pantalla la hora
                        } else {
                            tv_Hfinal.setText(hourOfDay + ":" + minute + " " + am);//imprime en pantalla la hora
                        }
                    } else if (timePicker.getHour() < 1) {
                        if (timePicker.getMinute() < 10) {//valida los minutos en formato 00
                            tv_Hfinal.setText("12:0" + minute + " " + am);//imprime en pantalla la hora
                        } else {
                            tv_Hfinal.setText("12:" + minute + " " + am);//imprime en pantalla la hora
                        }

                    } else if (timePicker.getHour() == 12) {

                        if (timePicker.getMinute() < 10) {//valida los minutos en formato 00
                            tv_Hfinal.setText(hourOfDay + ":0" + minute + " " + pm);//imprime en pantalla la hora
                        } else {
                            tv_Hfinal.setText(hourOfDay + ":" + minute + " " + pm);//imprime en pantalla la hora
                        }
                    } else {
                        hourOfDay = hourOfDay - 12;//valida pra formato de 12 horas
                        if (timePicker.getMinute() < 10) {//valida los minutos en formato 00
                            tv_Hfinal.setText(hourOfDay + ":0" + minute + " " + pm);//imprime en pantalla la hora
                        } else {
                            tv_Hfinal.setText(hourOfDay + ":" + minute + " " + pm);//imprime en pantalla la hora
                        }
                    }
                }
                //------------------------------------------------------------------------------------------------------------------
                if (h_inicio != 100 && h_final != 100) {

                    horas = h_final - h_inicio;//5-5
                    minutos = m_final - m_inicio;


                    if (horas < 0) {
                        horas = -(horas);
                        horas = 24 - horas;
                    } else if (horas == 0) {

                        if (m_final < m_inicio) {
                            horas = 24;
                        } else {
                            horas = 0;
                        }
                    }


                    if (minutos < 0) {
                        minutos = 60 + minutos;//suma los minutos
                        horas = horas - 1;//resta 1 a la hora para obtener la resta de las horas
                        if (horas < 0) {
                            horas = -(horas);

                            if (minutos < 10) {
                                tv_Htotales.setText("Tiempo total:  " + horas + " horas y 0" + minutos + " minutos");
                            } else {
                                tv_Htotales.setText("Tiempo total:  " + horas + " horas y " + minutos + " minutos");
                            }
                        } else {
                            if (minutos < 10) {
                                tv_Htotales.setText("Tiempo total:  " + horas + " horas y 0" + minutos + " minutos");
                            } else {
                                tv_Htotales.setText("Tiempo total:  " + horas + " horas y " + minutos + " minutos");
                            }
                        }
                    } else if (horas == 1) {
                        if (minutos == 1) {
                            tv_Htotales.setText("Tiempo total:  " + horas + " hora y 0" + minutos + " minuto");
                        } else {
                            tv_Htotales.setText("Tiempo total:  " + horas + " hora y 0" + minutos + " minutos");
                        }
                    } else if (minutos == 1) {
                        tv_Htotales.setText("Tiempo total:  " + horas + " horas y 0" + minutos + " minuto");
                    } else {
                        if (minutos < 10) {
                            tv_Htotales.setText("Tiempo total:  " + horas + " horas y 0" + minutos + " minutos");
                            //tv_Htotales.setText("Horas totales: " + horas + ":0" + minutos+" h");
                        } else {
                            tv_Htotales.setText("Tiempo total:  " + horas + " horas y " + minutos + " minutos");
                        }
                    }
                    precioT();//METODO PRECIO UWU
                }


            }

        });

        et_tarifa.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (!et_tarifa.getText().toString().isEmpty()) {
                    String tarifa_txt = et_tarifa.getText().toString();

                    //  tv_tarifauser.setText("Tarifa = $ "+ tarifa_txt);

                    precioT();


                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rb_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precioT();//metodo

            }
        });
        rb_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precioT();//metodo

            }
        });


//-------------------------------------boton flotante xd-------------------------------------------
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent compartirintent = new Intent(Intent.ACTION_SEND);
                compartirintent.setType("text/plain");
                compartirintent.putExtra(Intent.EXTRA_SUBJECT, "Insert Subjet here");
                String app_url = "https://www.instagram.com/robles_martin23/";
                compartirintent.putExtra(Intent.EXTRA_TEXT, app_url);
                startActivity(Intent.createChooser(compartirintent, "Compartir via"));*/

                Snackbar.make(view, "Gracias por compartir esta App", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void precioT() {
        //----------------------------------------------------------RadioNutton horas + caluculo precio---------------------------------------


        if (rb_hora.isChecked() && h_inicio != 100 && h_final != 100) {
            if (!et_tarifa.getText().toString().isEmpty()) {
                String tarifa_txt = et_tarifa.getText().toString();
                tarifa = Double.parseDouble(tarifa_txt.replaceAll("[^0-9.]", ""));

                horas_min_float = horas * 60;
                tarifa_float = tarifa / 60;
                minutostotales_float = horas_min_float + minutos;
                result = tarifa_float * minutostotales_float;

                if (result < 1) {
                    tv_Result.setText("$ 0" + df.format(result));//resultado xd
                } else {
                    tv_Result.setText("$ " + df.format(result));//resultado xd
                }

            }

            guardar();
        }

        //-------------------------------------------------------radiobutton minutos + calculo precio---------------------------------
        if (rb_min.isChecked() && h_inicio != 100 && h_final != 100) {

            if (!et_tarifa.getText().toString().isEmpty()) {
                String tarifa_txt = et_tarifa.getText().toString();
                tarifa = Double.parseDouble(tarifa_txt.replaceAll("[^0-9.]", ""));

                horas_min_float = horas * 60;
                minutostotales_float = horas_min_float + minutos;
                result = tarifa * minutostotales_float;

                if (result < 1) {
                    tv_Result.setText("$ 0" + df.format(result));//resultado xd
                } else {
                    tv_Result.setText("$ " + df.format(result));//resultado xd
                }

            }
        }
    }

    //-----------------------------------------------oculta el teclado ----------------------------------------------
    private void ocultarteclado() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            //Toast.makeText(this,"Teclado oculto",Toast.LENGTH_SHORT).show();
        }
    }


    //boton guardar
    public void guardar() {
        SharedPreferences preferences2 = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor = preferences2.edit();

        Obj_editor.putInt("horas", horas);
        Obj_editor.putInt("min", minutos);
        Obj_editor.putInt("h_inicio", h_inicio);
        Obj_editor.putInt("h_final", h_final);
        Obj_editor.putInt("m_inicio", m_inicio);
        Obj_editor.putInt("h_final", m_final);


        Obj_editor.putFloat("result", (float) result);

        Obj_editor.putString("tarifa", et_tarifa.getText().toString());
        Obj_editor.putString("Hini", tv_Hinicial.getText().toString());
        Obj_editor.putString("Hfini", tv_Hfinal.getText().toString());
        Obj_editor.putString("TH", tv_Htotales.getText().toString());

        Obj_editor.commit();//confirma que lo queremos guardar, referente a la linea anterior

        rb_RHI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precioT();//metodo

            }
        });

        // finish();
    }


}