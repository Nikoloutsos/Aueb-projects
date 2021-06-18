const params = new URLSearchParams(window.location.search)
const workID = params.get('workID')
const main = document.getElementById('main-content')
getBookDetailsByID()

function getBookDetailsByID() {
    var requestOptions = {
        method: 'GET'
    };

    fetch("http://127.0.0.1:3000/getFavoriteBooksById?workID=" + workID, requestOptions)
        .then(response => response.text())
        .then(result => loadUI(JSON.parse(result)))
        .catch(error => {});
}

function loadUI(json) {
    if (Array.isArray(json.titles.isbn)) {
        json.titles.isbn = json.titles.isbn[0]
    }

    let handleBarTemplate = document.getElementById("form-handlebars").innerHTML
    var templateScript = Handlebars.compile(handleBarTemplate);
    var html = templateScript(json);
    main.innerHTML = html;
}

function sendEditBook() {
    if (document.getElementById("titleInput").value == "") {
        swal("Empty field detected", "Book title cannot be empty");
        return
    }

    if (document.getElementById("authorInput").value == "") {
        swal("Empty field detected", "Author name cannot be empty");
        return
    }

    var myHeaders = new Headers();
    myHeaders.append("Accept", "*/*");
    myHeaders.append("Content-type", "application/json");

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: JSON.stringify({
            "bookID": workID,
            "title": document.getElementById("titleInput").value,
            "author": document.getElementById("authorInput").value,
            "review": document.getElementById("reviewTextarea").value,
        })
    };
    fetch("http://127.0.0.1:3000/editBook", requestOptions)
        .then(response => response.text())
        .then(result => {
            swal({
                title: "Book edited",
                text: "",
                icon: "success"
            }).then((willDelete) => {
                if (willDelete) {
                    window.location.href = "../favorites/favorites.html";
                }
            });
        })
        .catch(error => {});
}

function goBack() {
    window.location.href = "../favorites/favorites.html";
}