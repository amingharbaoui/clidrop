Dropzone.options.myAwesomeDropzone = {
    paramName: 'file',
    clickable: false,
    maxFiles: 4,
    init: function () {

        const dz = this;

        dz.on("success", function (file) {
            dz.removeFile(file);
        })

        dz.on("maxfilesexceeded", function (file) {
            dz.removefile(file);
        })
    }
};