import { apiFetch } from "./consumerApi";

const BASE_URL = "http://localhost:8082/api/v1/customer/information";

export function findAllPays(identity) {
  return apiFetch(`http://localhost:8080/${BASE_URL}/find-all-pays/${identity}`, {
  }, true);
}

export function savePayment(body) {
  return apiFetch(`http://localhost:8080/${BASE_URL}/save-payment`, {
    method: "POST",
    body: body
  }, true);
}

export function updateCustomer(body) {
  return apiFetch(`http://localhost:8080/${BASE_URL}/update-data`, {
    body: body,
    method: "POST"
  }, true);
}

export function finAllPurchases(identity) {
  return apiFetch(`http://localhost:8080/${BASE_URL}/get-payments/${identity}`, {
  }, true);
}
