package com.example.careium.frontend.home.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.careium.R
import com.example.careium.core.database.authentication.InternetConnection
import com.example.careium.core.database.authentication.Logout
import com.example.careium.core.database.authentication.SharedPreferences
import com.example.careium.core.database.realtime.DishImage
import com.example.careium.core.database.realtime.FoodData
import com.example.careium.core.models.DishClassification
import com.example.careium.core.models.DishNutritionRegression
import com.example.careium.databinding.ActivityMainBinding
import com.example.careium.databinding.LayoutFloatingMenuItemBinding
import com.example.careium.frontend.authentication.activities.SplashActivity
import com.example.careium.frontend.factory.*
import com.example.careium.frontend.home.fragments.*
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu

lateinit var nutritionViewModel: NutritionViewModel
lateinit var dishNameViewModel: DishNameViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var swipeListener: SwipeListener
    private lateinit var actionMenu: FloatingActionMenu
    private lateinit var dishImage: Bitmap
    private val permission = Permissions(this, this)

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide system bars
        hideNavigationAndStatusBars()

        // Bottom navigation menu items
        initializeBottomNavigation()

        // Add floating action menu
        buildFloatingActionMenu()

        // Add navigation view click actions
        makeNavigationViewItemsHooks()

        // Swipe touches listener
        swipeListener = SwipeListener(this)

        // Drawer menu icon click action
        binding.layoutToolbar.imageMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun initializeBottomNavigation() {
        // Adding menu items
        val bottomNavigation: MeowBottomNavigation = binding.bottomNavigation
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_round_home_24))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_round_recipes_24))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_round_diet_calendar_24))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_profile))

        // Bottom navigation show listener
        bottomNavigation.setOnShowListener {
            val fragment: Fragment? = when (it.id) {
                1 -> HomeFragment.newInstance()
                2 -> RecipeFragment.newInstance()
                3 -> ReportsFragment.newInstance()
                4 -> ProfileFragment.newInstance()
                else -> null
            }

            // Load Fragment
            if (fragment != null) {
                loadFragment(fragment)
            }
        }

        // Initialize home fragment as the default
        bottomNavigation.show(1, false)
        // Set notification count
        bottomNavigation.setCount(2, "5")

        // Bottom navigation click actions
        bottomNavigation.setOnClickMenuListener {
        }

        // Bottom navigation same button reselect action
        bottomNavigation.setOnReselectListener {
        }
    }

    private fun makeNavigationViewItemsHooks() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_item_home -> {
                    loadFragment(HomeFragment.newInstance())
                    binding.drawerLayout.closeDrawers()
                    binding.bottomNavigation.show(1, false)
                    true
                }
                R.id.menu_item_diary -> {
                    loadFragment(DiaryFragment.newInstance())
                    binding.drawerLayout.closeDrawers()
                    binding.bottomNavigation.show(-1, false)
                    true
                }
                R.id.menu_item_diet_calender -> {
                    loadFragment(CalenderFragment.newInstance())
                    binding.drawerLayout.closeDrawers()
                    binding.bottomNavigation.show(-1, false)
                    true
                }
                R.id.menu_item_photo_album -> {
                    loadFragment(AlbumFragment.newInstance())
                    binding.drawerLayout.closeDrawers()
                    binding.bottomNavigation.show(-1, false)
                    true
                }
                R.id.menu_item_reminder -> {
                    loadFragment(ReminderFragment.newInstance())
                    binding.drawerLayout.closeDrawers()
                    binding.bottomNavigation.show(-1, false)
                    true
                }
                R.id.menu_item_logout -> {
                    logout()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_main_frame, fragment)
            // TODO: Uncomment below line after finishing the design for all fragments
            //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // Hide both the navigation bar and the status bar.
        if (hasFocus)
            hideSystemBars()
    }

    private fun hideNavigationAndStatusBars() {
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            // The system bars will only be "visible" if none of the
            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
            if (visibility == 0)
                hideSystemBars()
        }
    }

    private fun hideSystemBars() {
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    // View.SYSTEM_UI_FLAG_FULLSCREEN or // for Status bar
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        // TODO: Uncheck this comment for swiping action
        // Handle Swipe Up action
        /*if (swipeListener.isSwipedUp(event))
            capturePlate()
        */

        // Collapse FAB Menu and clear the overlay
        if (actionMenu.isOpen) {
            binding.layoutMainFrameOverlay.visibility = View.INVISIBLE
            actionMenu.close(true)
        }

        return super.dispatchTouchEvent(event)
    }

    private fun capturePlate() {
        permission.checkPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,2)
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
            dishImage = data.extras?.get("data") as Bitmap
             val dish_image_uri = data.data
                if (dish_image_uri != null) {
                    DishImage.uploadImage(dishImage, dish_image_uri)
                }
        }

        try {
            val nutrition = DishNutritionRegression(this)
            val tensorBuffer1 = nutrition.loadImageBuffer(dishImage, 224, 224)
            val nutritionList = nutrition.predictNutritionModel(tensorBuffer1)

            val classifier = DishClassification(this)
            val tensorBuffer2 = classifier.loadImageBuffer(dishImage, 250, 250)
            val className = classifier.classifyDish(tensorBuffer2)

            // Update MutableLiveData to display the output
            nutritionViewModel.mutableNutrition.value = nutritionList
            dishNameViewModel.mutableDishName.value = className

            // store the food data in the firebase
            saveFoodDataInDatabase(className, nutritionList)

        } catch (e: Exception) {
            Log.d("DLModels", "Exception Occurred in DL Models")
        }

    }

    private fun saveFoodDataInDatabase(foodName: String, nutritionList: ArrayList<Float>) {
        val foodData = FoodData(foodName, nutritionList)
        foodData.saveFoodData()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun buildFloatingActionMenu() {
        // Building the primary button
        val primaryBtnIcon = ImageView(this)
        primaryBtnIcon.setImageDrawable(getDrawable(R.drawable.ic_round_add_24))
        val actionButton = FloatingActionButton.Builder(this).setContentView(primaryBtnIcon).build()
        val typedValue: TypedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorSecondary, typedValue, true)
        val colorSecondary: Int = typedValue.data
        actionButton.background.setTint(colorSecondary)

        val param = actionButton.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(10, 10, 10, 240)
        actionButton.layoutParams = param

        // Building up the menu
        // Item 1
        val item1 = inflateFABMenuItem(FABItem.WATER, R.drawable.ic_menu_water_48, "Water")
        // Item 2
        val item2 = inflateFABMenuItem(FABItem.DINNER, R.drawable.ic_menu_dinner_64, "Dinner")
        // Item 3
        val item3 = inflateFABMenuItem(FABItem.SNACK, R.drawable.ic_menu_snack_64, "Snack")
        // Item 4
        val item4 = inflateFABMenuItem(FABItem.LUNCH, R.drawable.ic_menu_lunch_64, "Lunch")
        // Item 5
        val item5 =
            inflateFABMenuItem(FABItem.BREAKFAST, R.drawable.ic_menu_breakfast_64, "Breakfast")
        // Item 6
        val item6 = inflateFABMenuItem(FABItem.TRACKER, R.drawable.ic_menu_camera_48, "Tracker")

        // Getting runtime screen width
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width: Float = displayMetrics.widthPixels.toFloat()
        val radius: Int = (width * 0.60f).toInt() // radius = 60% of the screen width

        // Attaching the menu to the primary button
        actionMenu = FloatingActionMenu.Builder(this)
            .setRadius(radius)
            .addSubActionView(item1)
            .addSubActionView(item2)
            .addSubActionView(item3)
            .addSubActionView(item4)
            .addSubActionView(item5)
            .addSubActionView(item6)
            .attachTo(actionButton)
            .build()

        actionButton.setOnClickListener {
            actionMenu.toggle(true)
            if (actionMenu.isOpen) {
                // add an overlay on opening
                binding.layoutMainFrameOverlay.visibility = View.VISIBLE
                // Close NavigationDrawer if opened
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
                    binding.drawerLayout.closeDrawers()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun inflateFABMenuItem(id: FABItem, drawableID: Int, label: String): View {
        val itemBinding = LayoutFloatingMenuItemBinding.inflate(layoutInflater)
        val params = FrameLayout.LayoutParams(-2, -2, 51)
        itemBinding.root.layoutParams = params
        itemBinding.fabItem.setImageDrawable(getDrawable(drawableID))
        itemBinding.textFabLabel.text = label

        // Click Actions
        itemBinding.fabItem.setOnClickListener {
            when (id) {
                FABItem.TRACKER ->
                    capturePlate()
                FABItem.BREAKFAST ->
                    Toast.makeText(this, "Breakfast Button Clicked !!", Toast.LENGTH_SHORT).show()
                FABItem.LUNCH ->
                    Toast.makeText(this, "Lunch Button Clicked !!", Toast.LENGTH_SHORT).show()
                FABItem.SNACK ->
                    Toast.makeText(this, "Snack Button Clicked !!", Toast.LENGTH_SHORT).show()
                FABItem.DINNER ->
                    Toast.makeText(this, "Dinner Button Clicked !!", Toast.LENGTH_SHORT).show()
                FABItem.WATER ->
                    Toast.makeText(this, "Water Button Clicked !!", Toast.LENGTH_SHORT).show()
            }
        }
        return itemBinding.root
    }

    private fun deleteEmailFromSharedPreference() {
        val preference = SharedPreferences(this)
        preference.delete()
    }

    private fun logout() {
        if(InternetConnection.isConnected(this)) {
            deleteEmailFromSharedPreference()
            signOut()
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
        else
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
    }

    private fun signOut(){
        val logout = Logout()
        logout.signOut()
    }
}
