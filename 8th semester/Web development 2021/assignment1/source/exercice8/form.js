var formRegister = document.querySelector("#register-form")
var password1 = document.querySelector("#password")
var password2 = document.querySelector("#password-validation")
var birthdayInput = document.querySelector("#birthday")
var joinReasonTextArea = document.querySelector("#join-reason")

birthdayInput.addEventListener("input", () => {
    let date = new Date(birthdayInput.value)
    let currentDate = new Date()
    let validDate = new Date(currentDate.getFullYear() - 18, currentDate.getMonth(), currentDate.getDay(), 0, 0, 0, 0)

    if (validDate < date) {
        birthdayInput.setCustomValidity("You have to be over 16 to join the club :) We are sorry about this ask your parents if you still wish to enter!")
        birthdayInput.reportValidity()
    }else {
        birthdayInput.setCustomValidity("")
        birthdayInput.reportValidity()
    }
})

joinReasonTextArea.addEventListener("input", () => {
    let str = joinReasonTextArea.value
    let count = str.split(" ").length

    if (count < 5) {
        joinReasonTextArea.setCustomValidity("You need to enter at least 5 words")
        joinReasonTextArea.reportValidity()
    }else {
        joinReasonTextArea.setCustomValidity("")
        joinReasonTextArea.reportValidity()
    }
    
})


password1.addEventListener("input", () => {
    password2.setCustomValidity("")
    password2.reportValidity()
})

password2.addEventListener("input", () => {
    password2.setCustomValidity("")
    password2.reportValidity()
})

formRegister.onsubmit = customSubmit

function customSubmit(event) {
    event.preventDefault()

    let areSamePasswords = password1.value === password2.value
    if (!areSamePasswords) {
        password2.setCustomValidity("Passwords are not identical. Make sure you are typing the same password")
        password2.reportValidity()
        return
    }else{
        password2.setCustomValidity("")
        password2.reportValidity()
    }

    Swal.fire(
        'You registered',
        'You have been added to the iOS club! You will be redirected in 3 seconds!',
        'success'
    )

    setTimeout(function() { window.location.href = "./index.html"; }, 3000);
}

