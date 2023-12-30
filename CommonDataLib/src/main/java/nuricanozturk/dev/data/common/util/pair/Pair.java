package nuricanozturk.dev.data.common.util.pair;

public class Pair<N, O>
{
    private final N m_first;
    private final O m_second;

    public Pair(N first, O second)
    {
        m_first = first;
        m_second = second;
    }

    public N getFirst()
    {
        return m_first;
    }

    public O getSecond()
    {
        return m_second;
    }

    @Override
    public String toString()
    {
        return "Pair{" +
                "m_first=" + m_first +
                ", m_second=" + m_second +
                '}';
    }

    public static <N, O> Pair<N, O> of(N first, O second)
    {
        return new Pair<>(first, second);
    }

    public static <N, O> Pair<N, O> of(Pair<N, O> pair)
    {
        return new Pair<>(pair.getFirst(), pair.getSecond());
    }


    public static <N, O> Pair<N, O> of(Pair<N, O> pair, O second)
    {
        return new Pair<>(pair.getFirst(), second);
    }


    public static <N, O> Pair<N, O> of(Pair<N, O> pair, N first, O second)
    {
        return new Pair<>(first, second);
    }

    public static <N, O> Pair<N, O> of(Pair<N, O> pair, Pair<N, O> pair2)
    {
        return new Pair<>(pair2.getFirst(), pair2.getSecond());
    }


    public static <N, O> Pair<N, O> of(Pair<N, O> pair, Pair<N, O> pair2, N first)
    {
        return new Pair<>(first, pair2.getSecond());
    }
}
