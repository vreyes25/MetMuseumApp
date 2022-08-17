package com.project.museumapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FeedbackFragment : Fragment() {

    private lateinit var firstAnswer : TextInputEditText
    private lateinit var secondAnswer : TextInputEditText
    private lateinit var thirdAnswer : TextInputEditText
    private lateinit var btnSubmit : TextView

    private val channelID = "channelID"
    private val channelName = "channelName"
    private val notificationID = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_feedback, container, false)

        createNotificationChannel()

        val notification = context?.let { it ->
            NotificationCompat.Builder(it, channelID).also {
                it.setContentTitle(getString(R.string.notificationTitle))
                it.setContentText(getString(R.string.notificationText))
                it.setSmallIcon(R.drawable.logo_red)
                it.priority = NotificationCompat.PRIORITY_HIGH
            }.build()
        }

        val notificationManager = context?.let { NotificationManagerCompat.from(it) }

        firstAnswer = view.findViewById(R.id.firstAnswer)
        secondAnswer = view.findViewById(R.id.secondAnswer)
        thirdAnswer = view.findViewById(R.id.thirdAnswer)
        btnSubmit = view.findViewById(R.id.submitButton)

        val db = Firebase.firestore

        btnSubmit.setOnClickListener {
            val favoriteDepartment = firstAnswer.text.toString()
            val museumComment = secondAnswer.text.toString()
            val appComment = thirdAnswer.text.toString()

            if (favoriteDepartment.isEmpty() && museumComment.isEmpty() && appComment.isEmpty()) {
                firstAnswer.error = getString(R.string.requiredField)
                secondAnswer.error = getString(R.string.requiredField)
                thirdAnswer.error = getString(R.string.requiredField)
            } else if (favoriteDepartment.isEmpty()) {
                firstAnswer.error = getString(R.string.requiredField)
            } else if (museumComment.isEmpty()) {
                secondAnswer.error = getString(R.string.requiredField)
            } else if (appComment.isEmpty()) {
                thirdAnswer.error = getString(R.string.requiredField)
            } else {
                val comment = hashMapOf(
                    "Departamento de arte favorito" to favoriteDepartment,
                    "¿Son importantes los museos?" to museumComment,
                    "Opinión sobre la aplicación" to appComment
                )

                db.collection("Comentarios")
                    .add(comment)
                    .addOnSuccessListener {
                        notificationManager?.notify(notificationID, notification!!)
                        firstAnswer.text = Editable.Factory.getInstance().newEditable("")
                        secondAnswer.text = Editable.Factory.getInstance().newEditable("")
                        thirdAnswer.text = Editable.Factory.getInstance().newEditable("")
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, getString(R.string.failureMessage), Toast.LENGTH_SHORT).show()
                        Log.w("Failure", "Error adding document", e)
                    }
            }
        }
        return view
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID, channelName, importance).apply {
                lightColor = R.color.splash
                enableLights(true)
            }

            val manager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}