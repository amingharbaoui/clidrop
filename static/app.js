// Button on click event, click on hidden input
const inputLeft = document.querySelector('.input_left');
const buttonLeft = document.querySelector(".button_left");

buttonLeft.addEventListener("click", function() {
    inputLeft.click();
})


inputLeft.addEventListener("change", function() {
    const files = Array.from(this.files).map(file => file.name).join(", ");
    document.querySelector(".file_info").textContent = files;
})
