package com.brandonhuddle.artemis.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brandonhuddle.artemis.R

const val CLIENT_ID = "4ukRA_tWwQDXnw"

class AddAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scopeValueString = listOf(
                "identity",
                "edit",
                "flair",
                "history",
                "modconfig",
                "modflair",
                "modlog",
                "modposts",
                "modwiki",
                "mysubreddits",
                "privatemessages",
                "read",
                "report",
                "save",
                "submit",
                "subscribe",
                "vote",
                "wikiedit",
                "wikiread",
        ).reduce { sum, element -> "$sum,$element" }
        val redditAuthorizeUrl =
                "https://www.reddit.com/api/v1/authorize.compact" +
                        "?client_id=" + CLIENT_ID +
                        "&response_type=code" +
                        "&state=none" +
                        "&redirect_uri=com.brandonhuddle.artemis://oauth2redirect" +
                        "&duration=permanent" +
                        "&scope=" + scopeValueString

        setContentView(R.layout.activity_add_account)
    }
}