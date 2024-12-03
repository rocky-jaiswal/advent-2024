export const readFile = async (path: string) => {
  const decoder = new TextDecoder('utf-8');
  const txt = await Deno.readFile(path);

  return decoder.decode(txt).trim().split('\n');
};
