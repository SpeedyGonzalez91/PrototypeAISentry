    function askQuestion() {
    const queryInput = document.getElementById("query-input");
    const messageBox = document.getElementById("message-box");

    // Get the user query from the input field
    const query = queryInput.value;

    // Clear the input field
    queryInput.value = "";

    // Append user's question to the message box
    appendMessage("You", query);

    // Call the AI service to get the response
    fetch("/query", {
    method: "POST",
    headers: {
    "Content-Type": "application/json",
},
    body: JSON.stringify(query),
})
    .then(response => response.text())
    .then(data => {
    // Append AI's response to the message box
    appendMessage("AI", JSON.parse(data).choices[0].message.content);
})
    .catch(error => {
    console.error(error);
    appendMessage("AI", "Error: Failed to get response from the AI service.");
});
}

    function appendMessage(sender, message) {
    const messageBox = document.getElementById("message-box");
    const messageElement = document.createElement("div");
    messageElement.classList.add("message");
    messageElement.textContent = `${sender}: ${message}`;
    messageBox.appendChild(messageElement);
    messageBox.scrollTop = messageBox.scrollHeight;
}