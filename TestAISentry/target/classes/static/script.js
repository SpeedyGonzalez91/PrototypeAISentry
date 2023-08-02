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
            appendMessage("AI", JSON.parse(data));
        })
        .catch(error => {
            console.error(error);
            appendMessage("AI", "Error: Failed to get response from the AI service.");
        });
}

function appendMessage(sender, message) {
    const messageBox = document.getElementById("message-box");

    // Create an inner scrollable element for the messages
    const messagesContainer = document.createElement("div");
    messagesContainer.classList.add("messages-container");

    // Check if the message is an object
    if (typeof message === "object") {
        // If it's an object, check if it has a 'response' property and display its value as paragraphs
        if (message.response) {
            const paragraphs = message.response.split('\n'); // Split the text into paragraphs using line breaks
            for (const paragraph of paragraphs) {
                const paragraphElement = document.createElement("p");
                paragraphElement.textContent = paragraph;
                messagesContainer.appendChild(paragraphElement);
            }
        } else {
            // If there's no 'response' property, convert the entire object to a JSON string for better display
            const jsonString = JSON.stringify(message, null, 2);
            const preElement = document.createElement("pre");
            preElement.textContent = jsonString;
            messagesContainer.appendChild(preElement);
        }
    } else {
        // Otherwise, simply display the message as it is
        const paragraphs = message.split('\n'); // Split the text into paragraphs using line breaks
        for (const paragraph of paragraphs) {
            const paragraphElement = document.createElement("p");
            paragraphElement.textContent = paragraph;
            messagesContainer.appendChild(paragraphElement);
        }
    }

    messageBox.appendChild(messagesContainer);

    // Scroll the inner messages container to the top
    messagesContainer.scrollTop = 0;
}





