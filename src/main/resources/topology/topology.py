from mininet.topo import Topo


class CustomTopology(Topo):

    def __init__(self):
        # Initialize topology
        Topo.__init__(self)

        # Adding hosts
        h1 = self.addHost('h1')
        h2 = self.addHost('h2')
        h3 = self.addHost('h3')
        h4 = self.addHost('h4')
        h5 = self.addHost('h5')
        h6 = self.addHost('h6')
        h7 = self.addHost('h7')
        h8 = self.addHost('h8')
        h9 = self.addHost('h9')
        h10 = self.addHost('h10')
        h11 = self.addHost('h11')
        h12 = self.addHost('h12')
        h13 = self.addHost('h13')
        h14 = self.addHost('h14')
        h15 = self.addHost('h15')
        h16 = self.addHost('h16')

        # Adding switches
        # s1 = self.addSwitch( 's1' )
        # s2 = self.addSwitch( 's2' )
        # s3 = self.addSwitch( 's3' )
        # s4 = self.addSwitch( 's4' )

        s5 = self.addSwitch('s5')
        s6 = self.addSwitch('s6')
        s7 = self.addSwitch('s7')
        s8 = self.addSwitch('s8')
        s9 = self.addSwitch('s9')
        s10 = self.addSwitch('s10')
        s11 = self.addSwitch('s11')
        s12 = self.addSwitch('s12')
        s13 = self.addSwitch('s13')
        s14 = self.addSwitch('s14')
        s15 = self.addSwitch('s15')
        s16 = self.addSwitch('s16')
        s17 = self.addSwitch('s17')
        s18 = self.addSwitch('s18')
        s19 = self.addSwitch('s19')
        s20 = self.addSwitch('s20')
        s21 = self.addSwitch('s21')
        s22 = self.addSwitch('s22')
        s23 = self.addSwitch('s23')
        s24 = self.addSwitch('s24')

        # Adding links between hosts and switches
        self.addLink(h1, s5)
        self.addLink(h2, s5)
        self.addLink(h3, s6)
        self.addLink(h4, s6)
        self.addLink(h5, s10)
        self.addLink(h6, s10)
        self.addLink(h7, s11)
        self.addLink(h8, s11)
        self.addLink(h9, s15)
        self.addLink(h10, s15)
        self.addLink(h11, s16)
        self.addLink(h12, s16)
        self.addLink(h13, s20)
        self.addLink(h14, s20)
        self.addLink(h15, s21)
        self.addLink(h16, s21)

        # Adding links between switches
        self.addLink(s5, s7)
        self.addLink(s5, s8)
        self.addLink(s6, s7)
        self.addLink(s6, s8)
        self.addLink(s10, s12)
        self.addLink(s10, s13)
        self.addLink(s11, s12)
        self.addLink(s11, s13)
        self.addLink(s15, s17)
        self.addLink(s15, s18)
        self.addLink(s16, s17)
        self.addLink(s16, s18)
        self.addLink(s20, s22)
        self.addLink(s20, s23)
        self.addLink(s21, s22)
        self.addLink(s21, s23)

        self.addLink(s7, s9)
        self.addLink(s7, s14)
        self.addLink(s8, s19)
        self.addLink(s8, s24)
        self.addLink(s12, s9)
        self.addLink(s12, s14)
        self.addLink(s13, s19)
        self.addLink(s13, s24)
        self.addLink(s17, s9)
        self.addLink(s17, s14)
        self.addLink(s18, s19)
        self.addLink(s18, s24)
        self.addLink(s22, s9)
        self.addLink(s22, s14)
        self.addLink(s23, s19)
        self.addLink(s23, s24)

        # set proper IP addresses
        # h1.cmd('ifconfig h1-eth0 10.0.0.1')
        # h2.cmd('ifconfig h2-eth0 10.5.0.1')
        # h3.cmd('ifconfig h3-eth0 10.100.0.1')
        # h4.cmd('ifconfig h4-eth0 10.145.0.1')


topos = {'fat_tree_topo': (lambda: CustomTopology())}
