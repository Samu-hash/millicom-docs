
const token = localStorage.getItem('token');

export async function apiFetch(url, options = {}, isToken) {
  try {
    const defaultHeaders = {
      "Content-Type": "application/json",
      "X-User-App": "my-app-millicom",
      "X-App-Version": "1.0-products",
      "X-Location": "SV",
    };

    const authHeader = isToken
      ? { Authorization: token }
      : { "X-Pass-App": "MY-P@ssW0rd-With.-Token" };

    const res = await fetch(url, {
      method: options.method || "GET",
      headers: {
        ...defaultHeaders,
        ...authHeader,
        ...(options.headers || {}),
      },
      body: options.body ? JSON.stringify(options.body) : null,
    });

    if (!res.ok) {
      const errorText = await res.text();
      throw new Error(`Error ${res.status}: ${errorText}`);
    }

    return await res.json();
  } catch (err) {
    console.error(`API fetch failed on ${url}:`, err.message);
    throw err;
  }
}
