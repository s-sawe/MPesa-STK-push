package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.DarajaListener
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.model.LNMExpress
import com.androidstudy.daraja.model.LNMResult
import com.androidstudy.daraja.util.Env
import com.androidstudy.daraja.util.TransactionType
import com.example.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var daraja: Daraja

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        daraja = Daraja.with(
            "AJlrwCKxcXupqeDF3zL9ENkG40gtaQGn",
            "Si3p2UrldG94vbIp",
            Env.SANDBOX, //for Test use Env.PRODUCTION when in production
            object : DarajaListener<AccessToken> {
                override fun onResult(accessToken: AccessToken) {

                    Toast.makeText(
                        this@MainActivity,
                        "MPESA TOKEN : ${accessToken.access_token}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(error: String) {

                }
            })

        binding.buttonpay.setOnClickListener {
            val phoneNumber = binding.phone.text.toString()
            val lnmExpress = LNMExpress(
                "174379",
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                TransactionType.CustomerPayBillOnline,
                "1",
                phoneNumber,
                "174379",
                phoneNumber,
                "https://mycallback.com",
                "001ABC",
                "Goods Payment"
            )

            daraja.requestMPESAExpress(lnmExpress,
                object : DarajaListener<LNMResult> {
                    override fun onResult(lnmResult: LNMResult) {


                        Toast.makeText(
                            this@MainActivity,
                            "Response here ${lnmResult.ResponseDescription}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onError(error: String) {

                        Toast.makeText(
                            this@MainActivity,
                            "Error here $error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }

    }
}