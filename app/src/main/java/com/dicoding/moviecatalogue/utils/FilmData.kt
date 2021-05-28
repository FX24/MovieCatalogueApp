package com.dicoding.moviecatalogue.utils

import com.dicoding.moviecatalogue.data.source.local.entity.FilmDetailEntity
import com.dicoding.moviecatalogue.data.source.local.entity.TvShowEntity
import com.dicoding.moviecatalogue.data.source.remote.response.*

object FilmData {

    fun generateMovies(): List<TvShowEntity> {

        val movie = ArrayList<TvShowEntity>()

        //1
        movie.add(
            TvShowEntity(
            567189,
            "Tom Clancy's Without Remorse",
            "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
            "2021-04-29"
        )
        )

        //2
        movie.add(
            TvShowEntity(
            460465,
            "Mortal Kombat",
            "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg",
            "2021-04-07"
        )
        )

        //3
        movie.add(
            TvShowEntity(
            804435,
            "Vanquish",
            "/AoWY1gkcNzabh229Icboa1Ff0BM.jpg",
            "2021-04-16"
        )
        )

        //4
        movie.add(
            TvShowEntity(
            399566,
            "Godzilla vs. Kong",
            "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
            "2021-03-24"
        )
        )

        //5
        movie.add(
            TvShowEntity(
            615678,
            "Thunder Force",
            "/duK11VQd4UPDa7UJrgrGx90xJOx.jpg",
            "2021-04-09"
        )
        )

        //6
        movie.add(
            TvShowEntity(
            615457,
            "Nobody",
            "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg",
            "2021-03-26",
        )
        )

        //7
        movie.add(
            TvShowEntity(
            793723,
            "Sentinelle",
            "/AmUGn1rJ9XDDP6DYn9OA2uV8MIg.jpg",
            "2021-03-05"
        )
        )

        //8
        movie.add(
            TvShowEntity(
            791373,
            "Zack Snyder's Justice League",
            "/tnAuB8q5vv7Ax9UAEje5Xi4BXik.jpg",
            "2021-03-18",
        )
        )

        //9
        movie.add(
            TvShowEntity(
            635302,
            "Demon Slayer -Kimetsu no Yaiba- The Movie: Mugen Train",
            "/h8Rb9gBr48ODIwYUttZNYeMWeUU.jpg",
            "2020-10-16"
        )
        )

        //10
        movie.add(
            TvShowEntity(
            544401,
            "Cherry",
            "/pwDvkDyaHEU9V7cApQhbcSJMG1w.jpg",
            "2021-02-26"
        )
        )
        return movie
    }

    fun generateTvShows() : List<TvShowEntity> {

        val tvshow = ArrayList<TvShowEntity>()

        //1
        tvshow.add(
            TvShowEntity(
            88396,
            "The Falcon and the Winter Soldier",
            "/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
            "2021-03-19",
        )
        )

        //2
        tvshow.add(
            TvShowEntity(
            95557,
            "Invincible",
            "/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg",
            "2021-03-26"
        )
        )

        //3
        tvshow.add(
            TvShowEntity(
            60735,
            "The Flash",
            "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
            "2014-10-07"
        )
        )

        //4
        tvshow.add(
            TvShowEntity(
            71712,
            "The Good Doctor",
            "/53P8oHo9cfOsgb1cLxBi4pFY0ja.jpg",
            "2017-09-25"
        )
        )


        //5
        tvshow.add(
            TvShowEntity(
            97180,
            "Selena: The Series",
            "/mYsWyfiIMxx4HDm0Wck7oJ9ckez.jpg",
            "2020-12-04"
        )
        )

        //6
        tvshow.add(
            TvShowEntity(
            105971,
            "The Bad Batch",
            "/WjQmEWFrOf98nT5aEfUfVYz9N2.jpg",
            "2021-05-04"
        )
        )

        //7
        tvshow.add(
            TvShowEntity(
            69478,
            "The Handmaid's Tale",
            "/oIkxqt6ug5zT5ZSUUyc1Iqopf02.jpg",
            "2017-04-26"
        )
        )

        //8
        tvshow.add(
            TvShowEntity(
            1416,
            "Grey's Anatomy",
            "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
            "2005-03-27"
        )
        )

        //9
        tvshow.add(
            TvShowEntity(
            93484,
            "Jupiter's Legacy",
            "/9yxep7oJdkj3Pla9TD9gKflRApY.jpg",
            "2021-05-07"
        )
        )

        //10
        tvshow.add(
            TvShowEntity(
            79008,
            "Luis Miguel: The Series",
            "/34FaY8qpjBAVysSfrJ1l7nrAQaD.jpg",
            "2018-04-22"
        )
        )
        return tvshow
    }

    fun generateMovieDetails(): FilmDetailEntity {

        val movie = FilmDetailEntity(
            567189,
            "Tom Clancy's Without Remorse",
            "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
            "An elite Navy SEAL uncovers an international conspiracy while seeking justice for the murder of his pregnant wife.",
            "2021-04-29",
            listOf(
                GenresItem("Action"),
                GenresItem("Adventure"),
                GenresItem("Thriller"),
                GenresItem("War")
            ),
            (7.3).toString()
        )
        return movie
    }

    fun generateTvShowDetails(): FilmDetailEntity {

        val tvshow = FilmDetailEntity(
            88396,
            "The Falcon and the Winter Soldier",
            "/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
            "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
            "2021-03-19",
            listOf(
                GenresItem("Sci-Fi & Fantasy"),
                GenresItem("Action & Adventure"),
                GenresItem("Drama"),
                GenresItem("War & Politics")
            ),
            (7.9).toString()
        )
        return tvshow
    }

    //REMOTE
    fun generateRemoteMovies(): List<MovieResultsItem> {

        val movie = ArrayList<MovieResultsItem>()

        //1
        movie.add(
            MovieResultsItem(
            567189,
            "Tom Clancy's Without Remorse",
            "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
            "2021-04-29"
        )
        )

        //2
        movie.add(
            MovieResultsItem(
            460465,
            "Mortal Kombat",
            "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg",
            "2021-04-07"
        )
        )

        //3
        movie.add(
            MovieResultsItem(
            804435,
            "Vanquish",
            "/AoWY1gkcNzabh229Icboa1Ff0BM.jpg",
            "2021-04-16"
        )
        )

        //4
        movie.add(
            MovieResultsItem(
            399566,
            "Godzilla vs. Kong",
            "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
            "2021-03-24"
        )
        )

        //5
        movie.add(
            MovieResultsItem(
            615678,
            "Thunder Force",
            "/duK11VQd4UPDa7UJrgrGx90xJOx.jpg",
            "2021-04-09"
        )
        )

        //6
        movie.add(
            MovieResultsItem(
            615457,
            "Nobody",
            "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg",
            "2021-03-26",
        )
        )

        //7
        movie.add(
            MovieResultsItem(
            793723,
            "Sentinelle",
            "/AmUGn1rJ9XDDP6DYn9OA2uV8MIg.jpg",
            "2021-03-05"
        )
        )

        //8
        movie.add(
            MovieResultsItem(
            791373,
            "Zack Snyder's Justice League",
            "/tnAuB8q5vv7Ax9UAEje5Xi4BXik.jpg",
            "2021-03-18",
        )
        )

        //9
        movie.add(
            MovieResultsItem(
            635302,
            "Demon Slayer -Kimetsu no Yaiba- The Movie: Mugen Train",
            "/h8Rb9gBr48ODIwYUttZNYeMWeUU.jpg",
            "2020-10-16"
        )
        )

        //10
        movie.add(
            MovieResultsItem(
            544401,
            "Cherry",
            "/pwDvkDyaHEU9V7cApQhbcSJMG1w.jpg",
            "2021-02-26"
        )
        )
        return movie
    }

    fun generateRemoteTvShows() : List<TvShowResultsItem> {

        val tvshow = ArrayList<TvShowResultsItem>()

        //1
        tvshow.add(TvShowResultsItem(
            88396,
            "The Falcon and the Winter Soldier",
            "/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
            "2021-03-19",
        ))

        //2
        tvshow.add(TvShowResultsItem(
            95557,
            "Invincible",
            "/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg",
            "2021-03-26"
        ))

        //3
        tvshow.add(TvShowResultsItem(
            60735,
            "The Flash",
            "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
            "2014-10-07"
        ))

        //4
        tvshow.add(TvShowResultsItem(
            71712,
            "The Good Doctor",
            "/53P8oHo9cfOsgb1cLxBi4pFY0ja.jpg",
            "2017-09-25"
        ))


        //5
        tvshow.add(TvShowResultsItem(
            97180,
            "Selena: The Series",
            "/mYsWyfiIMxx4HDm0Wck7oJ9ckez.jpg",
            "2020-12-04"
        ))

        //6
        tvshow.add(TvShowResultsItem(
            105971,
            "The Bad Batch",
            "/WjQmEWFrOf98nT5aEfUfVYz9N2.jpg",
            "2021-05-04"
        ))

        //7
        tvshow.add(TvShowResultsItem(
            69478,
            "The Handmaid's Tale",
            "/oIkxqt6ug5zT5ZSUUyc1Iqopf02.jpg",
            "2017-04-26"
        ))

        //8
        tvshow.add(TvShowResultsItem(
            1416,
            "Grey's Anatomy",
            "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
            "2005-03-27"
        ))

        //9
        tvshow.add(TvShowResultsItem(
            93484,
            "Jupiter's Legacy",
            "/9yxep7oJdkj3Pla9TD9gKflRApY.jpg",
            "2021-05-07"
        ))

        //10
        tvshow.add(TvShowResultsItem(
            79008,
            "Luis Miguel: The Series",
            "/34FaY8qpjBAVysSfrJ1l7nrAQaD.jpg",
            "2018-04-22"
        ))
        return tvshow
    }

    fun generateRemoteMovieDetails(movieId: Int): DetailMovieResponse {

        return DetailMovieResponse(
            movieId,
            "Tom Clancy's Without Remorse",
            "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
            "An elite Navy SEAL uncovers an international conspiracy while seeking justice for the murder of his pregnant wife.",
            "2021-04-29",
            listOf(
                GenresItem("Action"),
                GenresItem("Adventure"),
                GenresItem("Thriller"),
                GenresItem("War")
            ),
            7.3
        )
    }

    fun generateRemoteTvShowDetails(tvShowId: Int): DetailTVResponse{

        return DetailTVResponse(
            tvShowId,
            "The Falcon and the Winter Soldier",
            "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
            "/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
            "2021-03-19",
            listOf(
                GenresItem("Sci-Fi & Fantasy"),
                GenresItem("Action & Adventure"),
                GenresItem("Drama"),
                GenresItem("War & Politics")
            ),
            7.9
        )
    }
}