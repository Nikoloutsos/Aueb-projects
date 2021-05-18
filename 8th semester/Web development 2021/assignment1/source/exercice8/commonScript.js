var areImagesHidden = false

document.querySelector("#register-button").onclick = onSubscribeButton
document.querySelector("main").oncopy = onSomeoneTriedToCopy
document.querySelector("#hide-image-button").onclick = onHideImages
changeSubscribeTextDependingOnWeekDay()

function onSubscribeButton() {
    const email = document.querySelector("#email").value;
    regexEmail = "^[a-zA-Z]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"
    isEmail = email.match(regexEmail);
    if (isEmail) {
        Swal.fire(
            'You registered',
            'You have been added to the iOS club!',
            'success'
        )
        document.querySelector(".box-left").style.display = "none"
    }else {
        Swal.fire(
            'Incorrect email',
            "Email needs to contain special characters '.' and '@' ",
            'warning'
        )
    }    
}

function onSomeoneTriedToCopy() {
    Swal.fire(
        'Premium required',
        "Looks like copy-pasting is a feature for members only. Please consider supporting this blog!",
        'warning'
    )
}

function onHideImages() {
    areImagesHidden = !areImagesHidden
    var display = ""

    if (areImagesHidden){
        display =  "none"
        document.querySelector("#hide-image-button").value = "Show all images"
    }else{
        dispaly = "block"
        document.querySelector("#hide-image-button").value = "Hide all images"
    }

    var imgs = document.querySelectorAll("img")
    for (var i = 0; i < imgs.length; i++) {
        imgs[i].style.display = display
    }
}

function changeSubscribeTextDependingOnWeekDay() {
    var d = new Date();
    var n = d.getDay()

    var subsribeString = ""
    if (n == 0) {
        subsribeString = "What a sundayy, subscribe now"
    }else if (n % 2 == 0) {
        subsribeString = "Subscribe now and never miss anything"
    }else {
        subsribeString = "Subscribe for becoming a black belt iOS developer"   
    }
    document.querySelector("#subscribe-title").innerHTML = subsribeString
}