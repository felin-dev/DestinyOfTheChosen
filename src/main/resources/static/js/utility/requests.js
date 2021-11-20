export async function performGetRequest(url, headers) {
    return await performRequest('GET', url, headers);
}

export async function performPostRequest(url, headers, body) {
    return await performRequest('POST', url, headers, body);
}

export async function performPutRequest(url, headers, body) {
    return await performRequest('PUT', url, headers, body);
}

export async function performDeleteRequest(url, headers) {
    return await performRequest('DELETE', url, headers);
}

async function performRequest(method, url, headers, body = undefined) {
    const response = await fetch(url, {
        method,
        headers: {
            ...headers,
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(body)
    });

    const responseBody = tryParseJson(await response.text());

    if (response.status >= 200 && response.status < 300) {
        return responseBody;
    }

    throw new Error(responseBody.message);
}

function tryParseJson(data) {
    try {
        return JSON.parse(data);
    } catch (error) {
        return data;
    }
}