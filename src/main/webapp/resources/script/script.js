var count;
var counter;

function initTimer() {
    count = 10000000;
    counter = setInterval(timer, 3000);
}

function timer() {
    count = count - 1;

    clickSubmitButton();

    if (count <= 0) {
        clearInterval(counter);
    }
}

function clickSubmitButton() {
    var button = document.getElementById("j_idt7:submitButton");
    button.click();
}