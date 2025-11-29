const inputLeft = document.querySelector('.input_left');
const buttonLeft = document.querySelector(".button_left");


buttonLeft.addEventListener("click", () => {
    inputLeft.click();
});

inputLeft.addEventListener("change", () => {
    const files = inputLeft.files;
    if (!files || files.length === 0) {
        return;
    }

    const formData = new FormData();

    for (const file of files) {
        formData.append("file", file);
    }

    fetch('/upload', {
        method: 'POST',
        body: formData
    });
});