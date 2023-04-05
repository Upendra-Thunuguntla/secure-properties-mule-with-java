// Get the necessary HTML elements
const encryptBtn = document.getElementById("encrypt");
const decryptBtn = document.getElementById("decrypt");
const algorithmSelect = document.getElementById("algorithm");
const methodSelect = document.getElementById("method");
const keyInput = document.getElementById("key");
const inputStringBtn = document.getElementById("input-string");
const inputFileBtn = document.getElementById("input-file");
const inputWholeFileBtn = document.getElementById("input-whole-file");
const inputSection = document.getElementById("input-section");
const inputField = document.getElementById("input");
const inputFileEle = document.getElementById("inputFile");
const startButton = document.getElementById("start-button");
var inputFileName = "";

// Add event listeners to handle the dynamic behavior of the page
inputStringBtn.addEventListener("click", function () {
    console.log("Selected String");
    // inputField.type = "text";
    input_string_div.hidden = false;
    input_file_div.hidden = true;
    input_file_content_div.hidden = true;
    string_label.className = "btn btn-primary active";
    file_label.className = "btn btn-primary";
    whole_file_label.className = "btn btn-primary";
});

inputFileBtn.addEventListener("click", function () {
    console.log("Selected File");
    // inputField.type = "file";
    input_string_div.hidden = true;
    input_file_div.hidden = false;
    input_file_content_div.hidden = false;
    string_label.className = "btn btn-primary";
    file_label.className = "btn btn-primary active";
    whole_file_label.className = "btn btn-primary";
});

inputWholeFileBtn.addEventListener("click", function () {
    console.log("Selected Whole File");
    // inputField.type = "file";
    input_string_div.hidden = true;
    input_file_div.hidden = false;
    input_file_content_div.hidden = false;
    string_label.className = "btn btn-primary";
    file_label.className = "btn btn-primary";
    whole_file_label.className = "btn btn-primary active";
});

encryptBtn.addEventListener("click", function () {
    startButton.textContent = "Start Encryption";
    encrypt_label.className = "btn btn-success active";
    decrypt_label.className = "btn btn-success";
});

decryptBtn.addEventListener("click", function () {
    startButton.textContent = "Start Decryption";
    encrypt_label.className = "btn btn-success";
    decrypt_label.className = "btn btn-success active";
});

// Handle form submission
document.querySelector("form").addEventListener("submit", function (e) {
    e.preventDefault();

    // Get the values of the user inputs
    const operation = document.querySelector('input[name="options"]:checked').id;
    const algorithm = algorithmSelect.value;
    const method = methodSelect.value;
    const key = keyInput.value;
    // const selectedKey = selectKey.value;
    const inputType = document.querySelector('input[name="input-option"]:checked').id;
    const inputString = inputField.value;

    // Perform the encryption/decryption logic based on the user inputs
    if (encryptBtn.checked) {
        // Perform encryption
        console.log(`Performing encryption with algorithm=${algorithm}, method=${method}, key=${key}, inputType=${inputType}, input=${input}`);
    } else {
        // Perform decryption
        console.log(`Performing decryption with algorithm=${algorithm}, method=${method}, key=${key}, inputType=${inputType}, input=${input}`);
    }

    if (key == "" ) { //&& selectedKey == ""
        alert("Please select a key");
        return false;
    }

    if (key != "") {
        saveKeyAsFavorites(key);
    }

    if (inputType == "input-string" && inputField.value.trim() === "") {
        alert("Please enter the Value");
        inputField.focus();
        return false;
    }

    if (inputType != "input-string") {
        if (inputFileEle.files.length < 1 || inputTextArea.value === "") {
            alert("Please choose a File or Enter File content");
            return false;
        }
    }

    const payload = {
        "operation": operation,
        "algorithm": algorithm,
        "method": method,
        "key": key, //|| selectedKey,
        "inputType": inputType,
        "input": (inputType == "input-string") ? inputString : inputTextArea.value,
        "ext": inputFileName.split(".").pop(),
    };

    // startButton.disabled = true;
    console.log(JSON.stringify(payload));

    // makeApiCall(payload);
    makeFetchCall(payload);

});

var openFile = function (event) {
    var input = event.target;
    var fileName = input.value.split('\\').pop();
    inputFileName = fileName;
    document.getElementById("inputfilelabel").innerHTML = fileName;
    var reader = new FileReader();
    reader.onload = function () {
        var text = reader.result;
        var node = document.getElementById('inputTextArea');
        node.value = text;

    };
    reader.readAsText(input.files[0]);
};


function saveKeyAsFavorites(key) {
    // Get the existing array of unique values from the browser storage
    const favKeys = JSON.parse(localStorage.getItem('favKeys')) || [];

    if (favKeys.includes(key)) {
        return;
    } else {
        favKeys.push(key);
    }
    // Save the array of unique values to the browser storage
    console.log(favKeys);
    localStorage.setItem('favKeys', JSON.stringify(favKeys));
}

function retriveFavorites() {
    return JSON.parse(localStorage.getItem('favKeys')) || [];
}

function clearFavorites() {
    localStorage.setItem('favKeys', JSON.stringify([]));
}


function loadPage() {
    //Future Scope
    // const favs = retriveFavorites();
    // console.log(favs);
    // if (favs.length == 0) {
    //     document.getElementById("selectKey_div").hidden = true;
    //     document.getElementById("key_div").hidden = false;
    // } else {
    //     document.getElementById("selectKey_div").hidden = false;
    //     document.getElementById("key_div").hidden = true;
    // }

    // const opt = document.createElement('option');
    // opt.value = "--Other--";
    // opt.text = "--Other--";
    // selectKey.appendChild(opt);

    // favs.forEach(option => {
    //     const opt = document.createElement('option');
    //     opt.value = option;
    //     opt.text = option;
    //     selectKey.appendChild(opt);
    // });
}

function keyChanged(event) {
    if (event.value === "--Other--") {
        document.getElementById("selectKey_div").hidden = true;
        document.getElementById("key_div").hidden = false;
    } else {
        document.getElementById("selectKey_div").hidden = false;
        document.getElementById("key_div").hidden = true;
    }
}

function makeApiCall(payload) {
    // WARNING: For POST requests, body is set to null by browsers.
    var data = JSON.stringify(payload);

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            console.log(this.responseText);
        }
    });

    xhr.open("POST", "localhost:8080/process");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("crossorigin", "anonymous");

    xhr.send(data);
}

function makeFetchCall(payload) {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    // myHeaders.append("crossorigin", "anonymous");

    var raw = JSON.stringify(payload);

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    response = fetchCall("/api/process", requestOptions).then(response => {
        console.log(response);
        showResult(payload, response);
    });
}

function fetchCall(url, config) {
    return fetch(url, config).then(res => res.json());
}

function showResult(payload, response) {
    if (payload["inputType"] == "input-string") {
        console.log(response);
        string_result_body.hidden = false;
        string_result_response.innerHTML = response["response"];
    } else {
        console.log(response);
        file_result_body.hidden = false;
        file_result_response.value = response["response"];
    }
}

function copy_string(bool) {
    let response = string_result_response.innerHTML;
    if (bool) {
        response = "![" + response + "]";
    }
    // Copy the string to the clipboard
    navigator.clipboard.writeText(response)
        .then(() => {
            alert(response + " copied to clipboard.");
        })
        .catch((error) => {
            alert("Failed to copy string: ", error);
        });
}

function copy_file() {
    let response = file_result_response.value;
    // Copy the string to the clipboard
    navigator.clipboard.writeText(response)
        .then(() => {
            alert("Content copied to clipboard.");
        })
        .catch((error) => {
            alert("Failed to copy string: ", error);
        });
}

function download_file() {
    let response = file_result_response.value;

    // Create a Blob object from the content
    const blob = new Blob([response], { type: "text/plain" });

    // Create a link element and set its download attribute to the filename
    const link = document.createElement("a");

    link.download = (document.querySelector('input[name="options"]:checked').id) + "ed_" + inputFileName;

    // Set the href attribute of the link to the URL of the Blob object
    link.href = URL.createObjectURL(blob);

    // Click the link to download the file
    link.click();
}