import { apiFetch } from "./consumerApi";

const BASE_URL = "http://localhost:8081/api/v1/products";

export function getAllProducts() {
  return apiFetch(`http://localhost:8080/${BASE_URL}/products-availables`, {}, true);
}
