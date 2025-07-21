export default function LoginPage() {
  return (
    <div className="p-6 max-w-md mx-auto">
      <h1 className="text-2xl font-bold mb-4">Login com Email</h1>
      <form className="space-y-4">
        <input
          type="email"
          placeholder="Email"
          className="w-full border p-2 rounded"
        />
        <input
          type="password"
          placeholder="Senha"
          className="w-full border p-2 rounded"
        />
        <button className="bg-black text-white px-4 py-2 rounded w-full">
          Logar
        </button>
      </form>
      <p className="mt-4 text-center">Ou crie sua conta</p>
    </div>
  );
}
