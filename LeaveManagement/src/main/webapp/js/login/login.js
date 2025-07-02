/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const body = document.querySelector("body");
const modal = document.querySelector(".modal");
const modalButton = document.querySelector(".modal-button");
const closeButton = document.querySelector(".close-button");
const scrollDown = document.querySelector(".scroll-down");
let isOpened = false;

// Debug: Check if elements are found
if (!modal || !modalButton || !closeButton || !scrollDown || !body) {
  console.error("One or more elements not found:", { modal, modalButton, closeButton, scrollDown, body });
}

const openModal = () => {
  if (modal && body) {
    modal.classList.add("is-open");
    body.style.overflow = "hidden";
    isOpened = true;
    if (scrollDown) scrollDown.style.display = "none";
    console.log("Modal opened");
  }
};

const closeModal = () => {
  if (modal && body) {
    modal.classList.remove("is-open");
    body.style.overflow = "initial";
    isOpened = false;
    console.log("Modal closed");
  }
};

window.addEventListener("scroll", () => {
  if (window.scrollY > window.innerHeight / 3 && !isOpened) {
    console.log("Scroll triggered, opening modal");
    openModal();
  }
});

if (modalButton) {
  modalButton.addEventListener("click", () => {
    console.log("Modal button clicked");
    openModal();
  });
}

if (closeButton) {
  closeButton.addEventListener("click", () => {
    console.log("Close button clicked");
    closeModal();
  });
}

document.addEventListener("keydown", (evt) => {
  if (evt.key === "Escape") {
    console.log("Escape key pressed");
    closeModal();
  }
});

