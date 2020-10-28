package com.brandonhuddle.artemis.reddit.entities

// The fun thing about the comments response is it returns an array of 2 indexes with the first
// array being a different object than the second index... Why not just have it return an object
// with two members? Because this is the Reddit API and you shouldn't expect anything to be good
// This means we have to write another custom adapter to convert this from an array of different
// types to an object containing the two different types.
data class CommentsResponse(
    val submission: Thing<Listing<Link>>,
    val comments: Thing<Listing<Comment>>,
)