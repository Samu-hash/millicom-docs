import { apiFetch } from "./consumerApi";

const BASE_URL = "http://localhost:8081/api/v1/customer";

export function findAllPays(identity) {
  return apiFetch(`http://localhost:8080/${BASE_URL}/find-all-pays/${identity}`, {
  }, true);
}

export function savePayment(body) {
  return apiFetch(`http://localhost:8080/${BASE_URL}/save-payment`, {
    method:"POST",
    body:body
  }, true);
}
