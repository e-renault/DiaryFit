package ca.uqac.diaryfit

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.coroutines.awaitAll
import java.util.concurrent.Semaphore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        var profil: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseAuth.getInstance().uid?.let {
            FirebaseDatabase.getInstance().getReference("Users")
                .child(it)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        profil = snapshot.getValue(User::class.java)
                        Log.println(Log.DEBUG, "PROFIL", profil.toString())
                        binding = ActivityMainBinding.inflate(layoutInflater)
                        setContentView(binding.root)

                        val navView: BottomNavigationView = binding.navView

                        val navController = findNavController(R.id.nav_host_fragment_activity_main)
                        // Passing each menu ID as a set of Ids because each
                        // menu should be considered as top level destinations.
                        val appBarConfiguration = AppBarConfiguration(
                            setOf(
                                R.id.navigation_main, R.id.navigation_calendar, R.id.navigation_tools, R.id.navigation_stats, R.id.navigation_measures
                            )
                        )
                        setupActionBarWithNavController(navController, appBarConfiguration)
                        navView.setupWithNavController(navController)
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
            R.id.action_settings -> {
                //TODO implement settings menu
                Toast.makeText(this,"You have clicked options" + item.title, Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}