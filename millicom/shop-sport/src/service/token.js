import { apiFetch } from "./consumerApi";

const BASE_URL = "http://localhost:8082/api/v1/customer";

export function tokenService() {
  return apiFetch(`http://localhost:8080/${BASE_URL}/access/token`, {}, false);
}
