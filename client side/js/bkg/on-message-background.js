chrome.runtime.onMessage.addListener((request, sender, sendResponse)  => {
    if (request.action === "post_message") {
        port.postMessage(request.message);
        sendResponse("msg posted");
    }
    return true;
});
