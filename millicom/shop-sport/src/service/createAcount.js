import { apiFetch } from "./consumerApi";

const BASE_URL = "http://localhost:8082/api/v1/customer/information";


export function createService(name, last, mail, pass) {
  return apiFetch(`http://localhost:8080/${BASE_URL}/create-accunt`, {
    body:{
      "name":name,"lastname":last,
      "email":mail,"password":pass
    },
    method: "POST"
  }, true);
}
