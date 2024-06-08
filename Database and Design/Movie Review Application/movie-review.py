__author__ = "Easton Herbon"
__version__ = "3.12.3"
__brief__ = """This project implements the sqlite3 language with a movie review database, implementing queries and 
                the python language to allow full functionality off a virtual environment using the Flask plugin
            """

import sqlite3
from sqlite3 import Error

from flask import Flask, request, abort

app = Flask(__name__)

# Function to establish connection with SQLite database
def get_db_connection():
    #* LOCATION WILL NEED TO BE CHANGED, THIS WAS THE ONLY WAY I COULD ACCESS MY DATABASE
    conn = sqlite3.connect('C://Users//ehh20//Documents//movies.db')
    conn.row_factory = sqlite3.Row
    return conn


# *--ALL MOVIES FUNCTIONS--*

# Route to get all movies in the database
@app.route('/movies', methods=['GET'])
def get_movies():
    movies = []
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute('''
            SELECT Movies.*, GROUP_CONCAT(Actors.ActorID || '|' || Actors.First || '|' || Actors.Last || '|' || Actors.Date_Of_Birth) AS Actors
            FROM Movies
            LEFT JOIN MovieFeaturesActor ON Movies.MovieID = MovieFeaturesActor.MovieID
            LEFT JOIN Actors ON MovieFeaturesActor.ActorID = Actors.ActorID
            GROUP BY Movies.MovieID
        ''')
        rows = cursor.fetchall()
        for row in rows:
            movie = dict(row)
            movie_actors = []
            if movie['Actors']:
                actors_data = movie['Actors'].split(',')
                for actor_info in actors_data:
                    actor_id, first_name, last_name, date_of_birth = actor_info.split('|')
                    actor = {
                        'ActorID': int(actor_id),
                        'FirstName': first_name,
                        'LastName': last_name,
                        'DateOfBirth': date_of_birth
                    }
                    movie_actors.append(actor)
            movie['Actors'] = movie_actors
            movies.append(movie)
    except Error as e:
        print(f"Error retrieving movies: {e}")
        abort(500)
    finally:
        conn.close()
    return {'movies': movies}

# Route to query one movie
@app.route('/movies/<int:movieid>', methods=['GET'])
def get_movie(movieid):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute('''
            SELECT Movies.*, GROUP_CONCAT(Actors.ActorID || '|' || Actors.First || '|' || Actors.Last || '|' || Actors.Date_Of_Birth) AS Actors
            FROM Movies
            LEFT JOIN MovieFeaturesActor ON Movies.MovieID = MovieFeaturesActor.MovieID
            LEFT JOIN Actors ON MovieFeaturesActor.ActorID = Actors.ActorID
            WHERE Movies.MovieID = ?
            GROUP BY Movies.MovieID
        ''', (movieid,))
        movie = cursor.fetchone()
        if movie:
            movie_data = dict(movie)
            movie_actors = []
            if movie_data['Actors']:
                actors_data = movie_data['Actors'].split(',')
                for actor_info in actors_data:
                    actor_id, first_name, last_name, date_of_birth = actor_info.split('|')
                    actor = {
                        'ActorID': int(actor_id),
                        'FirstName': first_name,
                        'LastName': last_name,
                        'DateOfBirth': date_of_birth
                    }
                    movie_actors.append(actor)
            movie_data['Actors'] = movie_actors
            return movie_data
        else:
            abort(404)
    except Error as e:
        print(f"Error retrieving movie: {e}")
        abort(500)
    finally:
        conn.close()



#* UNSURE IF NEEDED
@app.route('/movies', methods=['POST'])
def add_movie():
    data = request.json
    name = data['name']
    release_year = data['release_year']
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute('INSERT INTO Movies (Name, ReleaseYear) VALUES (?, ?)', (name, release_year))
        conn.commit()
        return {'message': 'Movie added successfully'}, 201
    except Error as e:
        print(f"Error adding movie: {e}")
        abort(500)
    finally:
        conn.close()

#* UNSURE IF NEEDED
@app.route('/movies/<int:movieid>', methods=['PUT'])
def update_movie(movieid):
    data = request.json
    name = data['name']
    release_year = data['release_year']
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute('UPDATE Movies SET Name = ?, ReleaseYear = ? WHERE MovieID = ?', (name, release_year, movieid))
        conn.commit()
        return {'message': 'Movie updated successfully'}
    except Error as e:
        print(f"Error updating movie: {e}")
        abort(500)
    finally:
        conn.close()

#* UNSURE IF NEEDED
@app.route('/movies/<int:movieid>', methods=['DELETE'])
def delete_movie(movieid):
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute('DELETE FROM Movies WHERE MovieID = ?', (movieid,))
        conn.commit()
        return {'message': 'Movie deleted successfully'}
    except Error as e:
        print(f"Error deleting movie: {e}")
        abort(500)
    finally:
        conn.close()


# *--ALL REVIEW FUNCTIONS--*

# Route to retrieve all reviews for a specific movie
@app.route('/movies/<int:movieid>/reviews', methods=['GET'])
def get_reviews(movieid):
    reviews = []
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute('SELECT * FROM Reviews WHERE MovieID = ?', (movieid,))
        rows = cursor.fetchall()
        for row in rows:
            review = dict(row)
            reviews.append(review)
        return {'reviews': reviews}
    except Error as e:
        print(f"Error retrieving reviews: {e}")
        abort(500)
    finally:
        conn.close()

# Route to add a new review for a movie
@app.route('/movies/<int:movieid>/reviews', methods=['POST'])
def add_review(movieid):
    data = request.json
    stars = data['stars']
    description = data['description']
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute('INSERT INTO Reviews (Stars, Description, MovieID) VALUES (?, ?, ?)', (stars, description, movieid))
        conn.commit()
        return {'message': 'Review added successfully'}, 201
    except Error as e:
        print(f"Error adding review: {e}")
        abort(500)
    finally:
        conn.close()
    
""" ADD REVIEW TESTED USING

    {
    "stars": 4,
    "description": "Great movie wow"
    }

    SUCCESSFULLY ADDED THROUGH RAW JSON INPUT
"""

# *--ALL ACTOR FUNCTIONS--*

# Route to retrieve all actors
@app.route('/actors', methods=['GET'])
def get_actors():
    actors = []
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute('SELECT * FROM Actors')
        rows = cursor.fetchall()
        for row in rows:
            actor = dict(row)
            actors.append(actor)
        return {'actors': actors}
    except Error as e:
        print(f"Error retrieving actors: {e}")
        abort(500)
    finally:
        conn.close()



# *--ALL SEARCHES FUNCTIONS--*
# Route to search the database through various different methods
@app.route('/movies/search', methods=['GET'])
def search_movies():
    partial_title = request.args.get('partial_title')
    min_avg_stars = request.args.get('min_avg_stars')
    actor_name = request.args.get('actor_name')
    
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        sql = """
            SELECT Movies.*, COALESCE(AVG(Reviews.Stars), 0) AS avg_stars
            FROM Movies
            LEFT JOIN Reviews ON Movies.MovieID = Reviews.MovieID
            LEFT JOIN MovieFeaturesActor ON Movies.MovieID = MovieFeaturesActor.MovieID
            LEFT JOIN Actors ON MovieFeaturesActor.ActorID = Actors.ActorID
        """
        where_clauses = []
        params = []

        if partial_title:
            where_clauses.append("Movies.Name LIKE ?")
            params.append(f"%{partial_title}%")
        
        if min_avg_stars:
            try:
                min_avg_stars = float(min_avg_stars)
                where_clauses.append("(SELECT COALESCE(AVG(Reviews.Stars), 0) FROM Reviews WHERE Reviews.MovieID = Movies.MovieID) >= ?")
                params.append(min_avg_stars)
            except ValueError:
                return {"error": "Invalid min_avg_stars value"}, 400
        
        if actor_name:
            where_clauses.append("(Actors.First LIKE ? OR Actors.Last LIKE ? OR (Actors.First || ' ' || Actors.Last LIKE ?))")
            actor_search = f"%{actor_name}%"
            params.extend([actor_search, actor_search, actor_search])

        if where_clauses:
            sql += " WHERE " + " AND ".join(where_clauses)

        sql += " GROUP BY Movies.MovieID ORDER BY Movies.Name"
        
        cursor.execute(sql, tuple(params))
        rows = cursor.fetchall()
        movies = [dict(row) for row in rows]
        return {'movies': movies}
    except Error as e:
        print(f"Error searching movies: {e}")
        abort(500)
    finally:
        conn.close()



""" Did not know exactly if you wanted them to be separate or together, so I figured I would put both of them
    in route functions. The one above searches for movies with any combination of criteria, or any category
    chosen through the params or queries in Postman. The ones all work separately below.  
"""

# Route to search movie name (partial title)
@app.route('/movies/search/title', methods=['GET'])
def search_movies_by_title():
    partial_title = request.args.get('partial_title')       # Input to KEY, then Value is the partial title name
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM Movies WHERE Name LIKE ?", ('%' + partial_title + '%',))
        rows = cursor.fetchall()
        movies = [dict(row) for row in rows]
        return {'movies': movies}
    except Error as e:
        print(f"Error searching movies by title: {e}")
        abort(500)
    finally:
        conn.close()

# Route to search movies by minimum average star review
@app.route('/movies/search/min_avg_stars', methods=['GET'])
def search_movies_by_min_avg_stars():
    min_avg_stars = request.args.get('min_avg_stars')  # Input to KEY, then Value is the minimum average stars
    conn = get_db_connection()
    try:
        min_avg_stars = float(min_avg_stars)
        cursor = conn.cursor()
        sql = """
            SELECT Movies.*, COALESCE(AVG(Reviews.Stars), 0) AS avg_stars
            FROM Movies
            LEFT JOIN Reviews ON Movies.MovieID = Reviews.MovieID
            GROUP BY Movies.MovieID
            HAVING avg_stars >= ?
            ORDER BY Movies.Name
        """
        cursor.execute(sql, (min_avg_stars,))
        rows = cursor.fetchall()
        movies = [dict(row) for row in rows]
        return {'movies': movies}
    except Error as e:
        print(f"Error searching movies by minimum average star review: {e}")
        abort(500)
    finally:
        conn.close()


# Route to search movies by actor name in movie
@app.route('/movies/search/actor', methods=['GET'])
def search_movies_by_actor():
    actor_name = request.args.get('actor_name')  # Input to KEY, then Value is the actor's name
    conn = get_db_connection()
    try:
        cursor = conn.cursor()
        sql = """
            SELECT Movies.*
            FROM Movies
            INNER JOIN MovieFeaturesActor ON Movies.MovieID = MovieFeaturesActor.MovieID
            INNER JOIN Actors ON MovieFeaturesActor.ActorID = Actors.ActorID
            WHERE Actors.First LIKE ? OR Actors.Last LIKE ? OR
                  (Actors.First || ' ' || Actors.Last LIKE ?)
        """
        actor_search = '%' + actor_name + '%'
        cursor.execute(sql, (actor_search, actor_search, actor_search))
        rows = cursor.fetchall()
        movies = [dict(row) for row in rows]
        return {'movies': movies}
    except Error as e:
        print(f"Error searching movies by actor name: {e}")
        abort(500)
    finally:
        conn.close()
