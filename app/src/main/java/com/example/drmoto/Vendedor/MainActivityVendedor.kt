package com.example.drmoto.Vendedor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.drmoto.R
import com.example.drmoto.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentMisProductorV
import com.example.drmoto.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentOrdenesV
import com.example.drmoto.Vendedor.Nav_Fragments_Vendedor.FragmentIniciov
import com.example.drmoto.Vendedor.Nav_Fragments_Vendedor.FragmentMiTiendaV
import com.example.drmoto.Vendedor.Nav_Fragments_Vendedor.FragmentReseniasV
import com.example.drmoto.databinding.ActivityMainVendedorBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivityVendedor : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private lateinit var binding: ActivityMainVendedorBinding

    private var firebaseAuth : FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance();
        comprobarSesion();

        binding.navigationView.setNavigationItemSelectedListener ( this )

        var toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer


        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        
        replaceFragment(FragmentIniciov())
        binding.navigationView.setCheckedItem(R.id.op_inicio_v)

    }

    private fun cerrarSesion(){
        firebaseAuth!!.signOut()
        startActivity(Intent(applicationContext, LoginVendedorActivity::class.java))
        finish()
        Toast.makeText(applicationContext,  "Has cerrado sesión", Toast.LENGTH_SHORT).show()
    }

    private fun comprobarSesion() {
        /*Si el usuario no ha iniciado sesión*/
        if (firebaseAuth!!.currentUser == null) {
            startActivity(Intent(applicationContext, LoginVendedorActivity::class.java))
            Toast.makeText(applicationContext, "Vendedor no registro o no logeado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Vendedor en linea", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment,fragment)
            .commit()


    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.op_inicio_v->{
                replaceFragment(FragmentIniciov())
            }
            R.id.op_mi_tienda_v->{
                replaceFragment(FragmentMiTiendaV())
            }
            R.id.op_resenia_v->{
                replaceFragment(FragmentReseniasV())
            }
            R.id.op_cerrar_sesion_v->{
                cerrarSesion()
            }
            R.id.op_mis_productos_v->{
                replaceFragment(FragmentMisProductorV())
            }
            R.id.op_mis_ordenes_v->{
                replaceFragment(FragmentOrdenesV())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }
}