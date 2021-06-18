var latestApiResponse;
var searchFavTimeout;

const searchButton = document.getElementById("search-btn");
const searchInput = document.getElementById("search-input");
const main = document.getElementById("main-content");
onLoadFavoriteBooks()

function loadData(json) {

    if (!('work' in json)) {
        return;
    }

    if (json.work.length == 0) {
        showNoBooksFound()
    }

    if (!Array.isArray(json.work)) {
        json.work = [json.work];
    }
    console.log(json.work)
    for (i = 0; i < json.work.length; i++) {
        if (Array.isArray(json.work[i].titles.isbn)) {
            json.work[i].titles.isbn = json.work[i].titles.isbn[0]
        }
    }
    let a = document.getElementById("handlebars-demo").innerHTML
    var templateScript = Handlebars.compile(a);
    var html = templateScript(json);
    console.log(json)
    main.innerHTML = html;
    latestApiResponse = json
    getFavoriteBooksIdAndUpdateUI()
}

function onLoadFavoriteBooks(input) {
    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    var requestOptions = {
        method: 'GET',
        headers: myHeaders
    };

    fetch("http://127.0.0.1:3000/getFavoriteBooks", requestOptions)
        .then(response => response.text())
        .then(result => {
            loadData(JSON.parse(result))
        });
}

function onFavoriteClicked(bookID) {
    let favClickLabel = document.getElementById("toggle_favorite_" + bookID)
    if (favClickLabel.className == "favorite_off") {
        favClickLabel.className = "favorite_on"
        favClickLabel.innerHTML = "Remove from favorites"
    } else {
        favClickLabel.className = "favorite_off"
        favClickLabel.innerHTML = "Add to favorites"

    }
    console.log(favClickLabel.className)

    let bookJson = findBookFullJson(bookID)
    console.log(bookJson)

    var myHeaders = new Headers();
    myHeaders.append("Accept", "*/*");
    myHeaders.append("Content-type", "application/json");

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: JSON.stringify(bookJson)
    };
    fetch("http://127.0.0.1:3000/toggleFavoriteBook", requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
}

function getFavoriteBooksIdAndUpdateUI() {
    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    var requestOptions = {
        method: 'GET',
        headers: myHeaders
    };

    fetch("http://127.0.0.1:3000/getFavoriteBooksID", requestOptions)
        .then(response => response.text())
        .then(result => {
            let json = JSON.parse(result);
            for (i = 0; i < json.length; i++) {
                let currentWorkId = json[i]
                let favLabel = document.getElementById("toggle_favorite_" + currentWorkId)
                if (favLabel != null) {
                    favLabel.className = "favorite_on"
                    favLabel.innerHTML = "Remove from favorites"
                }
            }
        });
}

function findBookFullJson(bookID) {
    for (i = 0; i < latestApiResponse.work.length; i++) {
        if (latestApiResponse.work[i].workid == bookID) {
            return latestApiResponse.work[i];
        }
    }
}

function onKeyUpSearch() {
    clearTimeout(searchFavTimeout)
    searchFavTimeout = setTimeout(() => {
        searchForFavorite()
    }, 500);
}

function searchForFavorite() {
    let input = document.getElementById("search-input").value
    var requestOptions = {
        method: 'GET'
    };
    fetch("http://127.0.0.1:3000/searchFavoriteBooks?input=" + input, requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            loadData(JSON.parse(result))
        })
        .catch(error => showProblemServer());
}

function showNoBooksFound() {
    swal({
        title: "Hmm, no favorites found!",
        text: "Start by adding one to favorites",
        icon: "./../assets/imgs/book_empty.png"
    });
}

function showProblemServer() {
    swal({
        title: "Server is down",
        text: "Contact us at x@aueb.gr",
    });
}