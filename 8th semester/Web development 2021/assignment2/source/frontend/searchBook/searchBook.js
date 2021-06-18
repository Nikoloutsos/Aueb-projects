onSearchBook("science")

var latestApiResponse;

const searchButton = document.getElementById("search-btn");
const searchInput = document.getElementById("search-input");
const main = document.getElementById("main-content");

function onSearch() {
    let input = searchInput.value;
    onSearchBook(input);
}

function loadData(json) {
    if (!('work' in json)) {
        showNoBooksFound();
        return;
    }
    if (!Array.isArray(json.work)) {
        json.work = [json.work];
    }

    for (i = 0; i < json.work.length; i++) {
        if (Array.isArray(json.work[i].titles.isbn)) {
            json.work[i].titles.isbn = json.work[i].titles.isbn[0]
        }

    }

    let a = document.getElementById("handlebars-demo").innerHTML
    var templateScript = Handlebars.compile(a);
    var html = templateScript(json);
    main.innerHTML = html;

    latestApiResponse = json
    getFavoriteBooksIdAndUpdateUI()
}

function onSearchBook(input) {
    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    var requestOptions = {
        method: 'GET',
        headers: myHeaders
    };

    fetch("https://reststop.randomhouse.com/resources/works?start=0&max=8&search=" + input, requestOptions)
        .then(response => response.text())
        .then(result => {
            loadData(JSON.parse(result))
        });
}

function onFavoriteClicked(bookID) {
    // swal("was added to favorite")
    let favClickLabel = document.getElementById("toggle_favorite_" + bookID)
    if (favClickLabel.className == "favorite_off") {
        favClickLabel.className = "favorite_on"
        favClickLabel.innerHTML = "Remove from favorites"
    } else {
        favClickLabel.className = "favorite_off"
        favClickLabel.innerHTML = "Add to favorites"

    }

    let bookJson = findBookFullJson(bookID)

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
        .then(result => {})
        .catch(error => {});
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

/** View interactions */
function showNoBooksFound() {
    swal({
        title: "Hmm, no results found!",
        text: "Try again with a different keyword",
        icon: "./../assets/imgs/book_empty.png"
    });
}