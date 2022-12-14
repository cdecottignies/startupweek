export function getApiUrl({ protocol, hostname, port }) {
	const isProd = port === '',
		apiPort = isProd ? '' : ':8080';

	return `${protocol}//${hostname}${apiPort}/api/v1`;
}
