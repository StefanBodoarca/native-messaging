import {model} from './model/document.js'

$(document).ready(() => {
    $("#sendBtn").click(() => {
        chrome.runtime.sendMessage({
            action: "post_message",
            message: { text: $("#textMsg").val() }
        }, function(response) {
            console.log(response);
        });
    });

    $("#sendDocBtn").click(() => {
        const docName = $("#docName").val();
        if(docName == null) {
            alert("Please enter doc name");
        } else {
            let m = model;
            m._doc_id = docName;
            chrome.runtime.sendMessage({
                action: "post_message",
                message: {
                    "action": "TO_ES",
                    "document": m
                }
            }, function(response) {
                console.log(response);
            });

        }
    })
});
