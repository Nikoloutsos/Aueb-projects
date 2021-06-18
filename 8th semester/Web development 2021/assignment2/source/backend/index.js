const express = require('express')
var cors = require('cors');

var favoriteBooks = []

const app = express()
app.use(cors());
app.use(express.json());

const port = 3000

app.get('/getFavoriteBooks', (req, res) => {
    res.send({ 'work': favoriteBooks })
})

app.get('/getFavoriteBooksID', (req, res) => {
    var booksIDs = []
    for (i = 0; i < favoriteBooks.length; i++) {
        booksIDs.push(favoriteBooks[i].workid)
    }
    res.send(booksIDs)
})

app.get('/getFavoriteBooksById', (req, res) => {
    res.send(searchFavBooksById(req.query.workID))
})


app.get('/searchFavoriteBooks', (req, res) => {
    let userInput = req.query.input
    res.send({
        'work': searchForBooksByInput(userInput)
    })
})

app.post('/toggleFavoriteBook', (req, res) => {
    let bookId = getBookIdFromBookJson(req.body)
    if (isInFavoriteBooks(bookId)) {
        removeFromFavoritebooks(bookId)
    } else {
        insertInFavoriteBooks(req.body)
    }
    res.send({});
})

app.post('/editBook', (req, res) => {
    console.log(req.body)
    editBookWithID(req.body)
    res.send({});
})

app.listen(port, () => {
    console.log(`API listening at http://localhost:${port}`)
})

function isInFavoriteBooks(bookID) {
    for (i = 0; i < favoriteBooks.length; i++) {
        if (favoriteBooks[i].workid == bookID) {
            return true
        }
    }
    return false
}

function insertInFavoriteBooks(bookJson) {
    favoriteBooks.push(bookJson)
}

function removeFromFavoritebooks(bookID) {
    var filtered = favoriteBooks.filter(function(value, index, arr) {
        return value.workid != bookID
    });
    favoriteBooks = filtered
}

function getBookIdFromBookJson(bookJson) {
    return bookJson.workid
}

function searchForBooksByInput(input) {
    input = input.toUpperCase();
    var booksToBeReturned = []
    for (i = 0; i < favoriteBooks.length; i++) {
        let currentBook = favoriteBooks[i]
        let isFoundForTitle = currentBook.titleweb.toUpperCase().includes(input)
        let isFoundForAuthor = currentBook.authorweb.toUpperCase().includes(input)

        if (isFoundForAuthor || isFoundForTitle) {
            booksToBeReturned.push(currentBook)
        }
    }
    return booksToBeReturned
}

function searchFavBooksById(bookID) {
    for (i = 0; i < favoriteBooks.length; i++) {
        if (favoriteBooks[i].workid == bookID) {
            return favoriteBooks[i]
        }
    }
    return {}
}

function editBookWithID(json) {
    for (i = 0; i < favoriteBooks.length; i++) {
        if (favoriteBooks[i].workid == json.bookID) {
            favoriteBooks[i].titleweb = json.title
            favoriteBooks[i].authorweb = json.author
            favoriteBooks[i].review = json.review
        }
    }
}