const API_URL = 'http://localhost:8080/productos';

// Cargar productos al iniciar
window.onload = cargarProductos;

function cargarProductos() {
  fetch(API_URL)
    .then(response => response.json())
    .then(data => mostrarProductos(data))
    .catch(error => console.error('Error cargando productos:', error));
}

function mostrarProductos(productos) {
  const tabla = document.getElementById('tablaProductos');
  tabla.innerHTML = '';

  productos.forEach(p => {
    tabla.innerHTML += `
      <tr>
        <td>${p.id}</td>
        <td>${p.nombre}</td>
        <td>${p.precio}</td>
        <td>${p.cantidadDisponible}</td>
        <td>${p.descripcion}</td>
        <td>${p.categoria}</td>
        <td>
          <input type="number" min="1" id="cantidad-${p.id}" class="form-control d-inline-block" style="width: 70px;">
          <button class="btn btn-sm btn-outline-primary ms-2" onclick="venderProducto(${p.id})">
            <i class="fas fa-shopping-cart"></i>
          </button>
        </td>
      </tr>
    `;
  });
}

// Método que debe implementar el candidato
function buscarPorCategoria() {
  let categoria = document.getElementById('categoriaInput').value.trim();

  if (categoria === '') {
    document.getElementById('mensaje').textContent = 'Ingrese una categoría.';
    return;
  }

  fetch(`${API_URL}/categoria/${categoria}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Error en la búsqueda');
      }
      return response.json();
    })
    .then(data => {
      mostrarProductos(data);
      document.getElementById('mensaje').textContent = '';
    })
    .catch(error => {
      console.error('Error buscando productos:', error);
      document.getElementById('mensaje').textContent = 'Error al buscar productos por categoría.';
    });
}

function buscarId() {
  let idProducto = document.getElementById('idInput').value.trim();
  console.log('ID a buscar:', idProducto);
  if (idProducto === '') {
    document.getElementById('mensajeId').textContent = 'Ingrese un id.';
    return;
  }

  fetch(`${API_URL}/${idProducto}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Error en la búsqueda');
      }
      return response.json();
    })
    .then(data => {
      console.log('Producto encontrado:', data);
      mostrarProductos([data]); // Envolver en array
      document.getElementById('mensajeId').textContent = '';
    })
    .catch(error => {
      console.error('Error buscando producto por ID:', error);
      document.getElementById('mensajeId').textContent = 'Error al buscar producto por ID.';
    });
}

function limpiarFiltros() {
  // Limpiar inputs
  document.getElementById('categoriaInput').value = '';
  document.getElementById('idInput').value = '';

  // Limpiar mensajes
  document.getElementById('mensaje').textContent = '';
  document.getElementById('mensajeId').textContent = '';

  // Recargar todos los productos
  cargarProductos();
}

function venderProducto(id) {
  const cantidad = document.getElementById(`cantidad-${id}`).value;
  if (cantidad <= 0) {
    alert('La cantidad debe ser mayor a 0');
    return;
  }

  fetch(`${API_URL}/${id}/vender?cantidad=${cantidad}`, {
    method: 'PATCH'
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Error al vender el producto');
      }
      return response.json();
    })
    .then(data => {
      alert('Producto vendido exitosamente');
      cargarProductos(); // Recargar la lista para actualizar cantidades
    })
    .catch(error => {
      console.error('Error vendiendo producto:', error);
      alert('Error al vender el producto');
    });
}