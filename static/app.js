const inputLeft = document.querySelector('.input_left');
const buttonLeft = document.querySelector(".button_left");

buttonLeft.addEventListener("click", () => {
    inputLeft.click();
})


inputLeft.addEventListener("change", function () {
    const filesArray = Array.from(this.files);
    const fileNames = Array.from(this.files).map(file => file.name).join(", ");
    document.querySelector(".file_info").textContent = fileNames;

    if (filesArray.length > 4) {
        this.value = "";
    }
})

