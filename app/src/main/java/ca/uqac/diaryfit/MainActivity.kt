package ca.uqac.diaryfit

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.uqac.diaryfit.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    companion object {
        var profil: User? = null
        var navView: BottomNavigationView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val languageToLoad = "en" // your language

        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)

        FirebaseAuth.getInstance().uid?.let {
            FirebaseDatabase.getInstance().getReference("Users")
                .child(it)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val gson =
                            GsonBuilder().registerTypeAdapter(User::class.java, UserDeserializer())
                                .create()
                        val json = gson.toJson(snapshot.value)
                        val type = object : TypeToken<User?>() {}.type
                        profil = gson.fromJson(json, type)

                        Log.println(Log.DEBUG, "PROFIL", profil.toString())
                        binding = ActivityMainBinding.inflate(layoutInflater)
                        setContentView(binding.root)

                        navView = binding.navView

                        val navController = findNavController(R.id.nav_host_fragment_activity_main)
                        // Passing each menu ID as a set of Ids because each
                        // menu should be considered as top level destinations.
                        val appBarConfiguration = AppBarConfiguration(
                            setOf(
                                R.id.navigation_main, R.id.navigation_calendar, R.id.navigation_tools, R.id.navigation_stats
                            )
                        )
                        setupActionBarWithNavController(navController, appBarConfiguration)
                        navView!!.setupWithNavController(navController)
                    }
                    override fun onCancelled(error: DatabaseError) {
                        profil = null
                    }
                })
        }

    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean{
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_disconnect -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                Toast.makeText(this,"You have been disconnected", Toast.LENGTH_SHORT).show()
            }
            R.id.action_about -> {
                val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
                alertDialog.setTitle(applicationContext.resources.getString(R.string.about))
                alertDialog.setMessage(applicationContext.resources.getString(R.string.about_text))
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                alertDialog.show()
            }
            R.id.action_contact -> {
                val emails:ArrayList<String> = ArrayList<String>()
                emails.add(applicationContext.getString(R.string.support_email))
                val subject:String = applicationContext.getString(R.string.support_email_subject)
                composeEmail(emails, subject)
            }
        }
        return true
    }

    fun composeEmail(addresses: ArrayList<String>, subject: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, addresses)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}