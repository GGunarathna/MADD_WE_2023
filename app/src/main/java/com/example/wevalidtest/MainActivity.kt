package com.example.wevalidtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.wevalidtest.model.FormDat
import com.example.wevalidtest.model.FormData
import com.example.wevalidtest.model.validation.ValidationResult

class MainActivity : AppCompatActivity() {

  lateinit var edtStudentID:EditText
  lateinit var spnYear:Spinner
  lateinit var spnSemester:Spinner
  lateinit var cbAgree:CheckBox
  lateinit var btnSubmit:Button
  private var count=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtStudentID=findViewById(R.id.edtStudentID)
        spnYear=findViewById(R.id.spnYear)
        spnSemester=findViewById(R.id.spnSemester)
        cbAgree=findViewById(R.id.cbCondition)
        btnSubmit=findViewById(R.id.btnSubmit)
    }


    fun displayAlert(title:String, message:String)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK")
        {
                dialog, which -> // Do something when the "OK" button is clicked
        }
        val dialog = builder.create()
        dialog.show()


    }


    override fun onResume() {
        super.onResume()
        btnSubmit.setOnClickListener(View.OnClickListener {
            count=0

            val myForm= FormDat(edtStudentID.text.toString(),
                spnYear.selectedItem.toString(),
            spnSemester.selectedItem.toString(),
            cbAgree.isChecked)

            val studentIDValidation=myForm.validateStudentID()
            val yearValidation=myForm.validateYear()
            val semesterValidation=myForm.validateSemester()
            val agreeValidation=myForm.validateAgreement()

            when(studentIDValidation)
            {
                is ValidationResult.Empty ->
                    edtStudentID.error=studentIDValidation.errorMessage

                is ValidationResult.Invalid->
                    edtStudentID.error=studentIDValidation.errorMessage

                is ValidationResult.Valid->
                    count++;

            }

            when(yearValidation)
            {
                is ValidationResult.Empty->{
                    val tv:TextView=spnYear.selectedView as TextView
                    tv.error
                    tv.text=yearValidation.errorMessage

                }
                is ValidationResult.Invalid->{
                    val tv:TextView=spnYear.selectedView as TextView
                    tv.error
                    tv.text=yearValidation.errorMessage

                }
                is ValidationResult.Valid->
                    count++;

            }

            when(semesterValidation)
            {
                is ValidationResult.Empty->{
                    val tv:TextView=spnSemester.selectedView as TextView
                    tv.error
                    tv.text=semesterValidation.errorMessage

                }
                is ValidationResult.Invalid->{
                    val tv:TextView=spnSemester.selectedView as TextView
                    tv.error
                    tv.text=semesterValidation.errorMessage

                }
                is ValidationResult.Valid->
                    count++;

            }
            when(agreeValidation)
            {
                is ValidationResult.Empty->{
                    displayAlert("Error",agreeValidation.errorMessage)
                }

                is ValidationResult.Invalid->{}


                is ValidationResult.Valid->
                    count++;

            }
            if(count==4)
            {
                displayAlert("Success","You have successfully registered")
                val dataObject=FormData(edtStudentID.text.toString(),
                    Integer.parseInt(spnYear.selectedItem.toString()),
                    spnSemester.selectedItem.toString(),
                    cbAgree.isChecked
                    )

            }



        })
    }



















}