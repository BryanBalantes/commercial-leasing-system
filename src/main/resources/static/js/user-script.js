 let currentDeleteItem = null;

  // Open Modal
  function openModal(modalId) {
      const modal = document.getElementById(modalId);
      if (modal) {
          modal.classList.add('active');
      }
  }

  // Close Modal
  function closeModal(modalId) {
      const modal = document.getElementById(modalId);
      if (modal) {
          modal.classList.remove('active');
      }
  }

  // Close Modal on Backdrop Click
  function closeModalOnBackdrop(event, modalId) {
      if (event.target.id === modalId) {
          closeModal(modalId);
      }
  }

  // Confirm Delete
  function confirmDelete() {
      if (currentDeleteItem) {
          console.log(`[v0] Deleting ${currentDeleteItem.type}: ${currentDeleteItem.item}`);
          // TODO: Send DELETE request to backend
          closeModal('deleteConfirmModal');
          currentDeleteItem = null;
      }
  }


  function openDeleteConfirm(id) {
    if (confirm("Are you sure you want to delete this lease?")) {
        window.location.href = "/leases/delete/" + id;
    }
}

  // Close modal when pressing Escape key
  document.addEventListener('keydown', function(e) {
      if (e.key === 'Escape') {
          const activeModal = document.querySelector('.modal.active');
          if (activeModal) {
              activeModal.classList.remove('active');
          }
      }
  });

    function previewImage(event) {
    const input = event.target;
    const preview = document.getElementById("preview");

    if (input.files && input.files[0]) {
      const reader = new FileReader();
      reader.onload = function (e) {
        preview.src = e.target.result;
      };
      reader.readAsDataURL(input.files[0]);
    }
  }