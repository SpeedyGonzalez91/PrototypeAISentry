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

    if (typeof message === "object") {
        if (message.response) {
            const paragraphs = message.response.split('\n');
            for (const paragraph of paragraphs) {
                const paragraphElement = document.createElement("p");
                paragraphElement.textContent = paragraph;
                messagesContainer.appendChild(paragraphElement);
            }
        } else {
            const jsonString = JSON.stringify(message, null, 2);
            const preElement = document.createElement("pre");
            preElement.textContent = jsonString;
            messagesContainer.appendChild(preElement);
        }
    } else {
        const paragraphs = message.split('\n');
        for (const paragraph of paragraphs) {
            const paragraphElement = document.createElement("p");
            paragraphElement.textContent = paragraph;
            messagesContainer.appendChild(paragraphElement);
        }
    }

    // Add CSS classes based on the sender
    if (sender === "You") {
        messagesContainer.classList.add("user-message");
    } else if (sender === "AI") {
        messagesContainer.classList.add("ai-message");
    }

    messageBox.appendChild(messagesContainer);

    // Scroll the inner messages container to the top
    messagesContainer.scrollTop = 0;
}







