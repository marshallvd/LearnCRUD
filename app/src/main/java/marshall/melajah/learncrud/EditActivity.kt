package marshall.melajah.learncrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import marshall.melajah.learncrud.data.AppDatabase
import marshall.melajah.learncrud.data.entity.User

class EditActivity : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var btnSimpan: Button
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        fullName = findViewById(R.id.full_name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        btnSimpan = findViewById(R.id.btn_save)

        database = AppDatabase.getInstance(applicationContext)

        val intent = intent.extras
        if (intent!=null){
            val id = intent.getInt("id", 0)
            var user = database.userDao().get(id)

            fullName.setText(user.fullName)
            email.setText(user.email)
            phone.setText(user.telepon)
        }

        btnSimpan.setOnClickListener{
            if (fullName.text.isNotEmpty() && email.text.isNotEmpty() && phone.text.isNotEmpty()){
                if (intent != null){
                    // Edit Data
                    database.userDao().update(
                        User(
                            intent.getInt("id",0),
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )
                }else{
                    //Tambah data
                    database.userDao().insertAll(
                        User(
                            null,
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )
                }

                finish()
            }else{
                Toast.makeText(
                    applicationContext,
                    "Tolong lengkapi data terlebih dahulu!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}